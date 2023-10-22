package com.team2127.data.repository

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.login.LoginRequestDTO
import com.team2127.data.model.login.LoginResponseDTO
import com.team2127.data.source.LoginDataSource
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.LoginResInfo
import com.team2127.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
): LoginRepository{
    override suspend fun login(loginReqInfo: LoginReqInfo): Result<LoginResInfo> {
        val result = runCatching { dataSource.login(
            LoginRequestDTO(loginReqInfo.username, loginReqInfo.password)) }

        return if(result.isSuccess){
            val loginInfo = result.getOrThrow()
            Result.success(LoginResInfo(
                loginInfo.isSuccess,
                loginInfo.detail
            ))
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}
