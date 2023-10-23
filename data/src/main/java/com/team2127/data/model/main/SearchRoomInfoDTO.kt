package com.team2127.data.model.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRoomInfoDTO(
    @field:Json(name = "pk") val roomId: Int,
    @field:Json(name = "name") val roomName: Int,
    @field:Json(name = "attendee_number") val attendeeNumber: Int,
    @field:Json(name = "start_date") val startDate: String,
    @field:Json(name = "end_date") val endDate: String,
    @field:Json(name = "comment") val comment: String,
    @field:Json(name = "attendees") val attendeeList: List<AttendeeInfoDTO>,
)
