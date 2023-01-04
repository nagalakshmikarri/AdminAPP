package com.example.amplifieradmin.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amplifieradmin.data.api.ApiHelper
import com.example.amplifieradmin.data.repository.MainRepository
import com.example.amplifieradmin.viewmodel.HomeViewModel
import com.example.amplifieradmin.viewmodel.LoginViewModel


class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(MainRepository(apiHelper)) as T
        }else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalAccessException("Unknown class name")
    }
}