package com.team2127.data.repository

import com.team2127.data.model.login.LoginRequestDTO
import com.team2127.data.model.login.SignupRequestDTO
import com.team2127.data.source.LoginDataSource
import com.team2127.domain.exception.ForbiddenException
import com.team2127.domain.exception.InternetServerErrorException
import com.team2127.domain.exception.InvalidRequestException
import com.team2127.domain.exception.NotExistRoomException
import com.team2127.domain.exception.UnKnownException
import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.login.LoginReqInfo
import com.team2127.domain.model.login.SignupReqInfo
import com.team2127.domain.repository.LoginRepository
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
): LoginRepository{
    override suspend fun login(loginReqInfo: LoginReqInfo): Result<ResponseInfo> {
        val result = runCatching { dataSource.login(
            LoginRequestDTO(loginReqInfo.username, loginReqInfo.password)) }

        return if(result.isSuccess){
            val loginInfo = result.getOrThrow()
            Result.success(ResponseInfo(
                loginInfo.isSuccess,
                loginInfo.detail
            ))
        } else {
            Result.failure(handleLoginError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun signup(signupReqInfo: SignupReqInfo): Result<ResponseInfo> {
        val result = runCatching { dataSource.signup(
            SignupRequestDTO(
                signupReqInfo.username,
                signupReqInfo.password,
                signupReqInfo.password2,
                signupReqInfo.nickname
            )
        ) }

        return if (result.isSuccess){
            val signupInfo = result.getOrThrow()
            Result.success(ResponseInfo(
                signupInfo.isSuccess,
                signupInfo.detail
            ))
        } else {
            Result.failure(handleLoginError(result.exceptionOrNull()!!))
        }
    }

    private fun handleLoginError(t: Throwable): Throwable{
        return if(t is HttpException){
            handleErrorCode(t.code())
        } else{
            t
        }
    }
    private fun handleErrorCode(code: Int): Throwable{
        return when (code){
            HttpURLConnection.HTTP_BAD_REQUEST -> InvalidRequestException()
            else -> UnKnownException()
        }
    }
}
