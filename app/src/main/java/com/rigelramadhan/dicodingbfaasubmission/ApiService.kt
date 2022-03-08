package com.rigelramadhan.dicodingbfaasubmission
import com.rigelramadhan.dicodingbfaasubmission.model.UsersSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UsersSearchResponse>
}