package com.team2127.domain.model.main

data class GetRoomListInfo(
    val result: List<RoomResultInfo>,
    val count: Int,
    val next: Boolean,
    val previous: Boolean,
)
