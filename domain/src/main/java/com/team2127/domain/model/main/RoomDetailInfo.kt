package com.team2127.domain.model.main

data class RoomDetailInfo(
    val roomId: Int,
    val roomName: Int,
    val attendeeNumber: Int,
    val startDate: String,
    val endDate: String,
    val comment: String,
    val attendeeList: List<AttendeeInfo>,
    val result: List<String>,
)
