package com.team2127.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){


    fun login(){
        viewModelScope.launch {
            val request = loginUseCase(LoginReqInfo("admin", "12"))
            Log.d("login", "login : ${request.isLoginsSuccess}")
            Log.d("login", "loginDetail : ${request.detail}")
        }
    }
}
