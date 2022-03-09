package com.rigelramadhan.dicodingbfaasubmission
import com.rigelramadhan.dicodingbfaasubmission.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UsersSearchResponse>

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Call<UserResponse>

    @GET("users/{login}/repos")
    fun getUserRepos(@Path("login") login: String): Call<List<RepoResponseItem>>

    @GET("users/{login}/followers")
    fun getUserFollowers(@Path("login") login: String): Call<List<FollowersResponseItem>>

    @GET("users/{login}/following")
    fun getUserFollowings(@Path("login") login: String): Call<List<FollowingsResponseItem>>
}