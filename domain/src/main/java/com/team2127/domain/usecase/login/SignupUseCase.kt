package com.team2127.domain.usecase.login

import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.login.SignupReqInfo
import com.team2127.domain.repository.LoginRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(signupReqInfo: SignupReqInfo): Result<ResponseInfo> =
        repository.signup(signupReqInfo)
}
