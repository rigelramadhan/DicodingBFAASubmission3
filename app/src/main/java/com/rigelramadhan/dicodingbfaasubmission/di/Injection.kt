package com.rigelramadhan.dicodingbfaasubmission.di

import android.content.Context
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserRepository
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.UserDatabase
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.UserProfileDatabase
import com.rigelramadhan.dicodingbfaasubmission.data.remote.retrofit.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserProfileRepository
import com.rigelramadhan.dicodingbfaasubmission.util.AppExecutors

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }

    fun provideProfileRepository(context: Context): UserProfileRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserProfileDatabase.getInstance(context)
        val dao = database.userProfileDao()
        val appExecutors = AppExecutors()
        return UserProfileRepository.getInstance(apiService, dao, appExecutors)
    }
}