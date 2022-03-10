package com.rigelramadhan.dicodingbfaasubmission.networking
import com.rigelramadhan.dicodingbfaasubmission.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String
    ): Call<UsersSearchResponse>

    @GET("/users/{login}")
    fun getUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("/users/{login}/repos")
    fun getUserRepos(
        @Path("login") login: String,
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int
    ): Call<List<RepoResponseItem>>

    @GET("/users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login: String,
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int
    ): Call<List<FollowersResponseItem>>

    @GET("/users/{login}/following")
    fun getUserFollowings(
        @Path("login") login: String,
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int
    ): Call<List<FollowingsResponseItem>>
}