package com.team2127.data.model.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomResultDTO(
    @field:Json(name = "pk") val roomId: Int,
    @field:Json(name = "name") val roomName: String ,
    @field:Json(name = "attendee_number") val attendeeNumber: Int,
    @field:Json(name = "date") val date: String,
    @field:Json(name = "code") val code: String?,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "memo") val comment: String,
)
