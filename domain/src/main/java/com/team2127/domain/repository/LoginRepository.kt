package com.team2127.domain.repository

import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.SignupReqInfo

interface LoginRepository {
    suspend fun login(loginReqInfo: LoginReqInfo): Result<ResponseInfo>

    suspend fun signup(signupReqInfo: SignupReqInfo): Result<ResponseInfo>
}
