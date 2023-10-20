package com.team2127.domain.repository

import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.LoginResInfo

interface LoginRepository {
    suspend fun login(loginReqInfo: LoginReqInfo): Result<LoginResInfo>
}
