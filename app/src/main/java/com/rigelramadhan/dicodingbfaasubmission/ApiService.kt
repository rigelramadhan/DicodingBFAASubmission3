package com.rigelramadhan.dicodingbfaasubmission
import com.rigelramadhan.dicodingbfaasubmission.model.UsersListResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<UsersListResponseItem>>
}