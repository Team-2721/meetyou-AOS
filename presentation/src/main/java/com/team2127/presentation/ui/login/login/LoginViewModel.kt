package com.team2127.presentation.ui.login.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.usecase.login.LoginUseCase
import com.team2127.presentation.ui.util.MutableEventFlow
import com.team2127.presentation.ui.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(){

    val inputId = MutableStateFlow("")
    val inputPassword = MutableStateFlow("")

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    fun login(){
        viewModelScope.launch {
            val result = loginUseCase(LoginReqInfo(inputId.value, inputPassword.value))

            if (result.isSuccess){
                _event.emit(Event.LoginSuccess)
            } else {
                Log.d("login", "message : $result")
                _event.emit(Event.LoginFailed)
            }
        }
    }

    sealed class Event{
        object LoginSuccess : Event()
        object LoginFailed : Event()
    }
}
