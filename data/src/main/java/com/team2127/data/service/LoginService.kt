package com.team2127.data.service

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.login.LoginRequestDTO
import com.team2127.data.model.login.SignupRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ): ResponseDTO

    @POST("users/register")
    suspend fun signup(
        @Body signupRequestDTO: SignupRequestDTO
    ): ResponseDTO
}
