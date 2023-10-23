package com.team2127.domain.model.main

data class GenerateRoomReqInfo(
    val roomName: String,
    val attendeeNumber: Int,
    val startDate: String,
    val endDate: String,
    val comment: String,
)
