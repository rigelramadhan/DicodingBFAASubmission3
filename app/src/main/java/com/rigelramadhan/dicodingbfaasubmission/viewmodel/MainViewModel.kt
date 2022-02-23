package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.model.UsersListResponse
import com.rigelramadhan.dicodingbfaasubmission.model.UsersListResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _usersList = MutableLiveData<List<UsersListResponseItem>>()
    val usersList: LiveData<List<UsersListResponseItem>> = _usersList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getUsers()
    }

    private fun getUsers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UsersListResponseItem>> {
            override fun onResponse(
                call: Call<List<UsersListResponseItem>>,
                response: Response<List<UsersListResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usersList.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersListResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}