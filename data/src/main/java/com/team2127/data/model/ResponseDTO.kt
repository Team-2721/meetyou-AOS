package com.team2127.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDTO<T>(
    @field:Json(name = "ok") val isSuccess : Boolean,
    @field:Json(name = "detail") val detail : String?,
    @field:Json(name = "data") val data : List<T>?,
)
