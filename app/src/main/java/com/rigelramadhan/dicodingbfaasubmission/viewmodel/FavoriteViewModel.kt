package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteViewModel(application: Application) : ViewModel() {


    class ViewModelFactory (private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteViewModel(mApplication) as T
        }
    }
}