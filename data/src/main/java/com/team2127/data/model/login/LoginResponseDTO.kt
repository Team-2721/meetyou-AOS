package com.team2127.data.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDTO(
    @field:Json(name = "ok") val isLoginsSuccess: Boolean,
    @field:Json(name = "detail") val detail: String?,
)
