package com.team2127.data.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestDTO(
    @field:Json(name = "username") val username: String,
    @field:Json(name = "password") val password: String,
)
