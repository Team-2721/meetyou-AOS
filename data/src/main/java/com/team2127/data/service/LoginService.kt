package com.team2127.data.service

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.login.LoginRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ): ResponseDTO
}
