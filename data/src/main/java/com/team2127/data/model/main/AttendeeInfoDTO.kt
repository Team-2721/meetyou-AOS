package com.team2127.data.model.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttendeeInfoDTO(
    @field:Json(name = "pk") val attendeeId: Int,
    @field:Json(name = "nickname") val nickname: Int,
    @field:Json(name = "avatar") val avatar: Int,
    @field:Json(name = "vote") val vote: List<String>,
)
