package com.team2127.presentation.ui.login.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.SignupReqInfo
import com.team2127.domain.usecase.image.GetImageUseCase
import com.team2127.domain.usecase.login.LoginUseCase
import com.team2127.domain.usecase.login.SignupUseCase
import com.team2127.presentation.ui.util.MutableEventFlow
import com.team2127.presentation.ui.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase
): ViewModel() {

    val inputNickname = MutableStateFlow<String>("")
    val inputId = MutableStateFlow<String>("")
    val inputPassword = MutableStateFlow<String>("")
    val inputPasswordConfirm = MutableStateFlow<String>("")

    private val _signUpData = MutableStateFlow<SignupReqInfo>(SignupReqInfo("", "", "", ""))
    val signUpData : StateFlow<SignupReqInfo> get() = _signUpData

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    fun signUp(){
        viewModelScope.launch {
            val nickname = inputNickname.value
            val id = inputId.value
            val password = inputPassword.value
            val password2 = inputPasswordConfirm.value

            _signUpData.emit(SignupReqInfo(id, password, password2, nickname))

            if (nickname.isEmpty() || id.isEmpty() || password.isEmpty() || password2.isEmpty()){
                _event.emit(Event.SomethingEmpty)
            }

            if (isGoodNickname(nickname)){
                if (isGoodId(id)){
                    if (isGoodPassword(password, password2)){
                        val result = signupUseCase(signUpData.value)
                        if (result.isSuccess){
                            _event.emit(Event.SuccessSignUp)
                        }
                    }
                }
            }
        }
    }

    fun login(){
        viewModelScope.launch {
            val id = inputId.value
            val password = inputPassword.value

            val result = loginUseCase(LoginReqInfo(
                id,
                password
            ))

            if (result.isSuccess){
                _event.emit(Event.SuccessLogin)
            } else {
                _event.emit(Event.FailLogin)
            }
        }
    }


    private suspend fun isGoodNickname(nickname: String): Boolean{
        if (nickname.length > 21 || nickname.length < 2){
            _event.emit(Event.WrongLengthNickname)
            return false
        }
        if(toUseRegex(nickname, IS_GOOD_NICKNAME)){
            _event.emit(Event.HasConsonant)
            return false
        }
        return true
    }

    private suspend fun isGoodId(id: String): Boolean{
        if (id.length > 16 || id.length < 4){
            _event.emit(Event.WrongLengthId)
            return false
        }
        if (toUseRegex(id, IS_GOOD_ID).not()){
            _event.emit(Event.PleaseCheckId)
            return false
        }
        return true
    }

    private suspend fun isGoodPassword(password: String, passwordConfirm: String): Boolean{
        if (password.length > 16 || password.length < 8){
            _event.emit(Event.WrongLengthPassword)
            return false
        }
        if (toUseRegex(password, IS_GOOD_PASSWORD).not()){
            _event.emit(Event.PleaseCheckPassword)
            return false
        }
        if (password != passwordConfirm){
            _event.emit(Event.NotSamePassword)
            return false
        }
        return true
    }

    private fun toUseRegex(input: String, regex: String): Boolean {
        val regex = Regex(regex)
        return regex.matches(input)
    }
    sealed class Event(){
        object SuccessSignUp : Event()
        object SomethingEmpty : Event()
        object WrongLengthNickname: Event()
        object HasConsonant: Event()
        object WrongLengthId: Event()
        object PleaseCheckId: Event()
        object WrongLengthPassword: Event()
        object PleaseCheckPassword: Event()
        object NotSamePassword: Event()
        object SuccessLogin: Event()
        object FailLogin: Event()
    }

    companion object{
        private const val IS_GOOD_ID = "^[a-zA-Z0-9]{4,15}\$"
        private const val IS_GOOD_PASSWORD = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\]+\$"
        private const val IS_GOOD_NICKNAME = "^(?=.*[가-힣])(?=.*[a-zA-Z])(?!.*[ㄱ-ㅎㅏ-ㅣ]).*\\\$"
    }
}
