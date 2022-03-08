package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.dicodingbfaasubmission.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.model.UserResponse
import com.rigelramadhan.dicodingbfaasubmission.util.LoadingStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val profileUrl: String): ViewModel() {

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _isLoading = MutableLiveData<LoadingStatus>()
    val isLoading: LiveData<LoadingStatus> = _isLoading

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }

    init {
        loadUser()
    }

    private fun loadUser() {
        _isLoading.postValue(LoadingStatus.LOADING)
        val client = ApiConfig.getApiService().getUser(profileUrl)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.postValue(LoadingStatus.LOADED)
                if (response.isSuccessful) {
                    _user.value = response.body()
                    Log.d(TAG, "User: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    class ProfileViewModelFactory(private val profileUrl: String = "0") : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return super.create(modelClass) as T
        }
    }
}