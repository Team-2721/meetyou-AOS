package com.team2127.domain.model.login

data class SignupReqInfo(
    val username: String,
    val password: String,
    val password2: String,
    val nickname: String,
)
