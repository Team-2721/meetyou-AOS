package com.team2127.data.model.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRoomListDTO(
    @field:Json(name = "results") val result: List<RoomResultDTO>,
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "next") val next: Boolean,
    @field:Json(name = "previous") val previous: Boolean,
)
