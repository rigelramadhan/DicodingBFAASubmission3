package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.networking.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.model.ItemsItem
import com.rigelramadhan.dicodingbfaasubmission.model.UsersSearchResponse
import com.rigelramadhan.dicodingbfaasubmission.util.LoadingStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _usersList = MutableLiveData<List<ItemsItem>>()
    val usersList: LiveData<List<ItemsItem>> = _usersList

    private val _isLoading = MutableLiveData<LoadingStatus>()
    val isLoading: LiveData<LoadingStatus> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        queryUsers()
    }

    fun queryUsers(query: String = "a") {
        _isLoading.postValue(LoadingStatus.LOADING)
        val client = ApiConfig.getApiService().searchUsers(query, ApiConfig.TOKEN)
        client.enqueue(object : Callback<UsersSearchResponse> {
            override fun onResponse(
                call: Call<UsersSearchResponse>,
                response: Response<UsersSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.postValue(LoadingStatus.LOADED)
                    _usersList.value = response.body()?.items
                    Log.d(TAG, "${usersList.value}")
                } else {
                    _isLoading.postValue(LoadingStatus.FAILED)
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersSearchResponse>, t: Throwable) {
                _isLoading.postValue(LoadingStatus.FAILED)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}