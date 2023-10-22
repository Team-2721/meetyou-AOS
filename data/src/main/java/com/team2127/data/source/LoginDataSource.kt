package com.team2127.data.source

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.login.LoginRequestDTO
import com.team2127.data.model.login.LoginResponseDTO
import com.team2127.data.service.LoginService
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val service: LoginService
) {
    suspend fun login(loginRequestDTO: LoginRequestDTO): ResponseDTO =
        service.login(loginRequestDTO)
}
