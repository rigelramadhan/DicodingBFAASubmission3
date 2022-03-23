package com.rigelramadhan.dicodingbfaasubmission.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity
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
    private val appExecutors: AppExecutors
) {
    private var profile = MediatorLiveData<UserProfileEntity>()

    private val repos = MutableLiveData<Result<List<RepoResponseItem>>>()

    private val followings = MutableLiveData<Result<List<FollowingsResponseItem>>>()

    private val followers = MutableLiveData<Result<List<FollowersResponseItem>>>()

    fun getProfile(login: String): LiveData<UserProfileEntity> {
        val client = apiService.getUser(login, ApiConfig.TOKEN)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val profile = response.body()!!
                    appExecutors.diskIO.execute {
                        val profileEntity = UserProfileEntity(
                            profile.gistsUrl, profile.reposUrl, profile.followingUrl, profile.twitterUsername,
                            profile.bio, profile.createdAt, profile.login, profile.type, profile.blog,
                            profile.subscriptionsUrl, profile.updatedAt, profile.siteAdmin, profile.company,
                            profile.id, profile.publicRepos, profile.gravatarId, profile.email.toString(), profile.organizationsUrl,
                            profile.hireable.toString(), profile.starredUrl, profile.followersUrl, profile.publicGists,
                            profile.url, profile.receivedEventsUrl, profile.followers, profile.avatarUrl,
                            profile.eventsUrl, profile.htmlUrl, profile.following, profile.name, profile.location,
                            profile.nodeId
                        )
                        userProfileDao.deleteProfile(profileEntity.login)
                        userProfileDao.insertProfile(profileEntity)
                    }
                } else {
                    Log.e(TAG, "Failed: message(\"${response.message()}\")")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }
        })

        val localProfileData = userProfileDao.getProfile(login)
        Log.d(TAG, localProfileData.toString())
        profile.addSource(localProfileData) {
            profile.value = it
        }
        return profile
    }

    fun getFollowings(user: UserProfileEntity): LiveData<Result<List<FollowingsResponseItem>>> {
        followings.value = Result.Loading
        val client = ApiConfig.getApiService().getUserFollowings(user.login, ApiConfig.TOKEN, user.following)
        client.enqueue(object : Callback<List<FollowingsResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingsResponseItem>>,
                response: Response<List<FollowingsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val followingsData = response.body()
                    followings.value = Result.Success(followingsData!!)
                } else {
                    followings.value = Result.Error(response.message())
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingsResponseItem>>, t: Throwable) {
                followings.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })


        return followings
    }

    fun getFollowers(user: UserProfileEntity): LiveData<Result<List<FollowersResponseItem>>> {
        followers.value = Result.Loading
        val client = ApiConfig.getApiService().getUserFollowers(user.login, ApiConfig.TOKEN, user.followers)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val followersData = response.body()
                    followers.value = Result.Success(followersData!!)
                    Log.d(TAG, "Followers Response Count: ${response.body()?.size}")
                } else {
                    followers.value = Result.Error(response.message())
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                followers.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

        return followers
    }

    fun getRepos(user: UserProfileEntity): LiveData<Result<List<RepoResponseItem>>> {
        repos.value = Result.Loading
        val client = ApiConfig.getApiService().getUserRepos(user.login, ApiConfig.TOKEN, user.publicRepos)
        client.enqueue(object : Callback<List<RepoResponseItem>> {
            override fun onResponse(
                call: Call<List<RepoResponseItem>>,
                response: Response<List<RepoResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val reposData = response.body()
                    repos.value = Result.Success(reposData!!)
                    Log.d(TAG, "Repos Response Count: ${response.body()?.size}")
                } else {
                    repos.value = Result.Error(response.message())
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RepoResponseItem>>, t: Throwable) {
                repos.value = Result.Error(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

        return repos
    }

    companion object {
        private const val TAG = "UserProfileRepository"

        @Volatile
        private var instance: UserProfileRepository? = null
        fun getInstance(
            apiService: ApiService,
            userProfileDao: UserProfileDao,
            appExecutors: AppExecutors
        ): UserProfileRepository =
            instance ?: synchronized(this) {
                instance ?: UserProfileRepository(apiService, userProfileDao, appExecutors)
            }.also { instance = it }
    }
}