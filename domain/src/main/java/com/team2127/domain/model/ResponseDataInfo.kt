package com.team2127.domain.model

data class ResponseDataInfo<T>(
    val isSuccess : Boolean,
    val detail : String?,
    val data : T,
)
