package com.example.amplifieradmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.data.model.LoginReq
import com.example.amplifieradmin.data.repository.MainRepository
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: MainRepository) : ViewModel() {
    val loginIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            loginIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.LoginUser -> fetchUser(it.username, it.password)
                    else -> {}
                }
            }
        }
    }
    private fun fetchUser(username: String, password: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = LoginReq(username, password)
                val response=repository.getLoginUser(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.LoginUser(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }
    }



}