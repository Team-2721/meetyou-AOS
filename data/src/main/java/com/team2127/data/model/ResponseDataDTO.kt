package com.team2127.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.team2127.data.model.main.GetRoomListDTO

@JsonClass(generateAdapter = true)
data class ResponseDataDTO<T>(
    @field:Json(name = "ok") val isSuccess : Boolean,
    @field:Json(name = "detail") val detail : String?,
    @field:Json(name = "data") val data : T,
)
