package com.rigelramadhan.dicodingbfaasubmission.networking
import com.rigelramadhan.dicodingbfaasubmission.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(@Query("q") query: String): Call<UsersSearchResponse>

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getUser(@Path("login") login: String): Call<UserResponse>

    @GET("users/{login}/repos")
    @Headers("Authorization: token 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")
    fun getUserRepos(
        @Path("login") login: String,
        @Query("per_page") perPage: Int
    ): Call<List<RepoResponseItem>>

    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("login") login: String,
        @Query("per_page") perPage: Int
    ): Call<List<FollowersResponseItem>>

    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowings(
        @Path("login") login: String,
        @Query("per_page") perPage: Int
    ): Call<List<FollowingsResponseItem>>
}