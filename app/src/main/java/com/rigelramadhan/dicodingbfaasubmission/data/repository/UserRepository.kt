package com.rigelramadhan.dicodingbfaasubmission.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.UserDao
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.UsersSearchResponse
import com.rigelramadhan.dicodingbfaasubmission.data.remote.retrofit.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.data.remote.retrofit.ApiService
import com.rigelramadhan.dicodingbfaasubmission.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor (
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<UserEntity>>>()
    private val queryResult = MutableLiveData<Result<List<UserEntity>>>()
    private val favoriteResult = MediatorLiveData<Result<List<UserEntity>>>()

    fun getUsers(): LiveData<Result<List<UserEntity>>> {
        result.value = Result.Loading
        val client = apiService.searchUsers("a", ApiConfig.TOKEN)
        client.enqueue(object : Callback<UsersSearchResponse> {
            override fun onResponse(
                call: Call<UsersSearchResponse>,
                response: Response<UsersSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val usersList = ArrayList<UserEntity>()

                    appExecutors.diskIO.execute {
                        users?.forEach { user ->
                            val isFavorite = userDao.isUserFavorite(user.login)
                            val userEntity = UserEntity(
                                user.gistsUrl,
                                user.reposUrl,
                                user.followingUrl,
                                user.starredUrl,
                                user.login,
                                user.followersUrl,
                                user.type,
                                user.url,
                                user.subscriptionsUrl,
                                user.score,
                                user.receivedEventsUrl,
                                user.avatarUrl,
                                user.eventsUrl,
                                user.htmlUrl,
                                user.siteAdmin,
                                user.id,
                                user.gravatarId,
                                user.nodeId,
                                user.organizationsUrl,
                                isFavorite,
                            )
                            usersList.add(userEntity)
                        }
                        userDao.deleteAllUsers()
                        userDao.insertUsers(usersList)
                    }
                } else {
                    Log.e(TAG, "Failed: message(\"${response.message()}\")")
                }
            }

            override fun onFailure(call: Call<UsersSearchResponse>, t: Throwable) {
                    result.value = Result.Error(t.message.toString())
            }
        })

        val localData = userDao.getUsers()
        result.addSource(localData) { users ->
            result.value = Result.Success(users)
        }
        return result
    }

    fun queryUser(query: String): LiveData<Result<List<UserEntity>>> {
        queryResult.value = Result.Loading
        val client = apiService.searchUsers(query, ApiConfig.TOKEN)
        client.enqueue(object : Callback<UsersSearchResponse> {
            override fun onResponse(
                call: Call<UsersSearchResponse>,
                response: Response<UsersSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val usersList = ArrayList<UserEntity>()

                    appExecutors.diskIO.execute {
                        users?.forEach { user ->
                            if (userDao.isUserExists(user.login)) {
                                usersList.add(userDao.getUser(user.login))
                            } else {
                                val isFavorite = userDao.isUserFavorite(user.login)
                                val userEntity = UserEntity(
                                    user.gistsUrl,
                                    user.reposUrl,
                                    user.followingUrl,
                                    user.starredUrl,
                                    user.login,
                                    user.followersUrl,
                                    user.type,
                                    user.url,
                                    user.subscriptionsUrl,
                                    user.score,
                                    user.receivedEventsUrl,
                                    user.avatarUrl,
                                    user.eventsUrl,
                                    user.htmlUrl,
                                    user.siteAdmin,
                                    user.id,
                                    user.gravatarId,
                                    user.nodeId,
                                    user.organizationsUrl,
                                    isFavorite,
                                )
                                usersList.add(userEntity)
                            }
                        }
                        queryResult.postValue(Result.Success(usersList))
                    }
                } else {
                    Log.e(TAG, "Failed: message(\"${response.message()}\")")
                }
            }

            override fun onFailure(call: Call<UsersSearchResponse>, t: Throwable) {
                queryResult.value = Result.Error(t.message.toString())
            }
        })

        return queryResult
    }

    fun isDataEmpty(): Boolean {
        return userDao.getUsers().value.isNullOrEmpty()
    }

    fun insertUser(userEntity: UserEntity) {
        appExecutors.diskIO.execute {
            userDao.insertUsers(listOf(userEntity))
        }
    }

    fun getFavoriteUsers(): LiveData<Result<List<UserEntity>>> {
        favoriteResult.value = Result.Loading

        val localData = userDao.getFavoriteUsers()
        favoriteResult.addSource(localData) { users ->
            favoriteResult.value = Result.Success(users)
        }
        return favoriteResult
    }

    fun setFavoriteUser(user: UserEntity, isFavorite: Boolean) {
        appExecutors.diskIO.execute {
            user.isFavorite = isFavorite
            userDao.userFavorite(user)
        }
    }

    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}