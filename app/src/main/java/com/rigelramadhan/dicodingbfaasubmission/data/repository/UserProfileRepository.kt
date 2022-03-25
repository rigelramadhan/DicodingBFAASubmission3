package com.rigelramadhan.dicodingbfaasubmission.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.UserDao
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.UserProfileDao
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.FollowersResponseItem
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.FollowingsResponseItem
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.RepoResponseItem
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.UserResponse
import com.rigelramadhan.dicodingbfaasubmission.data.remote.retrofit.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.data.remote.retrofit.ApiService
import com.rigelramadhan.dicodingbfaasubmission.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileRepository private constructor(
    private val apiService: ApiService,
    val userProfileDao: UserProfileDao,
    val userDao: UserDao,
    private val appExecutors: AppExecutors
) {
    private var profile = MutableLiveData<UserProfileEntity>()

    private val repos = MutableLiveData<Result<List<RepoResponseItem>>>()

    private val followings = MutableLiveData<Result<List<FollowingsResponseItem>>>()

    private val followers = MutableLiveData<Result<List<FollowersResponseItem>>>()

    fun getProfile(login: String): LiveData<UserProfileEntity> {
        Log.d(TAG, "Login: $login")
        val client = apiService.getUser(login, ApiConfig.TOKEN)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val profileData = response.body()!!
                    appExecutors.diskIO.execute {
                        val user = if (userDao.isUserExists(profileData.login))
                            userDao.getUser(profileData.login).isFavorite else false
                        val profileEntity = UserProfileEntity(
                            profileData.gistsUrl, profileData.reposUrl, profileData.followingUrl, profileData.twitterUsername,
                            profileData.bio, profileData.createdAt, profileData.login, profileData.type, profileData.blog,
                            profileData.subscriptionsUrl, profileData.updatedAt, profileData.siteAdmin, profileData.company,
                            profileData.id, profileData.publicRepos, profileData.gravatarId, profileData.email?.toString(), profileData.organizationsUrl,
                            profileData.hireable?.toString(), profileData.starredUrl, profileData.followersUrl, profileData.publicGists,
                            profileData.url, profileData.receivedEventsUrl, profileData.followers, profileData.avatarUrl,
                            profileData.eventsUrl, profileData.htmlUrl, profileData.following, profileData.name, profileData.location,
                            profileData.nodeId, user
                        )
                        this@UserProfileRepository.profile.postValue(profileEntity)
                        userProfileDao.deleteProfile(profileEntity.login)
                        userProfileDao.insertProfile(profileEntity)
                    }

                    appExecutors.networkIO.execute {
                        loadRepos(profileData)
                        loadFollowings(profileData)
                        loadFollowers(profileData)
                    }
                } else {
                    Log.e(TAG, "Failed: message(\"${response.message()}\")")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }
        })

        appExecutors.diskIO.execute {
            if (userProfileDao.isProfileExists(login)) {
                profile.postValue(userProfileDao.getProfile(login).value)
            }
        }

        return profile
    }

    fun setFavoriteUser(login: String, isFavorite: Boolean) {
        appExecutors.diskIO.execute {
            userDao.updateUserFavorite(login, isFavorite)
        }
    }

    fun getRepos(): LiveData<Result<List<RepoResponseItem>>> = repos
    fun getFollowings(): LiveData<Result<List<FollowingsResponseItem>>> = followings
    fun getFollowers(): LiveData<Result<List<FollowersResponseItem>>> = followers

    private fun loadFollowings(user: UserResponse) {
        followings.postValue(Result.Loading)
        val client = ApiConfig.getApiService().getUserFollowings(user.login, ApiConfig.TOKEN, user.following)
        client.enqueue(object : Callback<List<FollowingsResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingsResponseItem>>,
                response: Response<List<FollowingsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val followingsData = response.body()
                    followings.postValue(Result.Success(followingsData!!))
                } else {
                    followings.postValue(Result.Error(response.message()))
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingsResponseItem>>, t: Throwable) {
                followings.postValue(Result.Error(t.message.toString()))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun loadFollowers(user: UserResponse){
        followers.postValue(Result.Loading)
        val client = ApiConfig.getApiService().getUserFollowers(user.login, ApiConfig.TOKEN, user.followers)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val followersData = response.body()
                    followers.postValue(Result.Success(followersData!!))
                    Log.d(TAG, "Followers Response Count: ${response.body()?.size}")
                } else {
                    followers.postValue(Result.Error(response.message()))
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                followers.postValue(Result.Error(t.message.toString()))
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun loadRepos(user: UserResponse) {
        repos.postValue(Result.Loading)
        val client = ApiConfig.getApiService().getUserRepos(user.login, ApiConfig.TOKEN, user.publicRepos)
        client.enqueue(object : Callback<List<RepoResponseItem>> {
            override fun onResponse(
                call: Call<List<RepoResponseItem>>,
                response: Response<List<RepoResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val reposData = response.body()
                    repos.postValue(Result.Success(reposData!!))
                    Log.d(TAG, "Repos Response Count: ${response.body()?.size}")
                } else {
                    repos.postValue(Result.Error(response.message()))
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RepoResponseItem>>, t: Throwable) {
                repos.postValue(Result.Error(t.message.toString()))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "UserProfileRepository"

        @Volatile
        private var instance: UserProfileRepository? = null
        fun getInstance(
            apiService: ApiService,
            userProfileDao: UserProfileDao,
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserProfileRepository =
            instance ?: synchronized(this) {
                instance ?: UserProfileRepository(apiService, userProfileDao, userDao, appExecutors)
            }.also { instance = it }
    }
}