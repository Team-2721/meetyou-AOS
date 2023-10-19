package com.team2127.domain.usecase.login

import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.LoginResInfo
import com.team2127.domain.repository.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(loginReqInfo: LoginReqInfo): LoginResInfo =
        repository.login(loginReqInfo)
}
