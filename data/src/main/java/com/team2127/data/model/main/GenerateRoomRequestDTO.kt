package com.team2127.data.model.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerateRoomRequestDTO(
    @field:Json(name = "name") val roomName: String,
    @field:Json(name = "attendee_number") val attendeeNumber: Int,
    @field:Json(name = "start_date") val startDate: String,
    @field:Json(name = "end_date") val endDate: String,
    @field:Json(name = "comment") val comment: String,
)
