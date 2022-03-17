package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.rigelramadhan.dicodingbfaasubmission.networking.ApiConfig
import com.rigelramadhan.dicodingbfaasubmission.model.*
import com.rigelramadhan.dicodingbfaasubmission.util.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val login: String): ViewModel() {

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _repos = MutableLiveData<List<RepoResponseItem>>()
    val repos: LiveData<List<RepoResponseItem>> = _repos

    private val _followings = MutableLiveData<List<FollowingsResponseItem>>()
    val followings: LiveData<List<FollowingsResponseItem>> = _followings

    private val _followers = MutableLiveData<List<FollowersResponseItem>>()
    val followers: LiveData<List<FollowersResponseItem>> = _followers

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
        val client = ApiConfig.getApiService().getUser(login, ApiConfig.TOKEN)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                    loadFragmentData()
                    Log.d(TAG, "User: ${response.body()}")
                } else {
                    _isLoading.postValue(LoadingStatus.FAILED)
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.postValue(LoadingStatus.FAILED)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun loadFragmentData() {
        viewModelScope.launch(Dispatchers.IO) {
            loadRepos()
        }

        viewModelScope.launch(Dispatchers.IO) {
            loadFollowings()
        }

        viewModelScope.launch(Dispatchers.IO) {
            loadFollowers()
        }
    }

    private fun loadFollowings() {
        val client = ApiConfig.getApiService().getUserFollowings(login, ApiConfig.TOKEN, user.value!!.following)
        client.enqueue(object : Callback<List<FollowingsResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingsResponseItem>>,
                response: Response<List<FollowingsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _followings.value = response.body()
                } else {
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingsResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun loadFollowers() {
        val client = ApiConfig.getApiService().getUserFollowers(login, ApiConfig.TOKEN, user.value!!.followers)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    Log.d(TAG, "Followers Response Count: ${response.body()?.size}")
                } else {
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun loadRepos() {
        _isLoading.postValue(LoadingStatus.LOADING)
        val client = ApiConfig.getApiService().getUserRepos(login, ApiConfig.TOKEN, user.value!!.publicRepos)
        client.enqueue(object : Callback<List<RepoResponseItem>> {
            override fun onResponse(
                call: Call<List<RepoResponseItem>>,
                response: Response<List<RepoResponseItem>>
            ) {
                _isLoading.postValue(LoadingStatus.LOADED)
                if (response.isSuccessful) {
                    _repos.value = response.body()
                    Log.d(TAG, "Repos Response Count: ${response.body()?.size}")
                } else {
                    Log.e(TAG, "onResponseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RepoResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    class ProfileViewModelFactory(private val login: String = "0") : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(login) as T
        }
    }
}