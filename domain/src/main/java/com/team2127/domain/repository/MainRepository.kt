package com.team2127.domain.repository

import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.main.GenerateRoomReqInfo
import com.team2127.domain.model.main.GetRoomListInfo
import com.team2127.domain.model.main.RoomDetailInfo
import com.team2127.domain.model.main.SearchRoomInfo


interface MainRepository {
    suspend fun generateRoom(generateRoomReqInfo: GenerateRoomReqInfo): Result<ResponseInfo>

    suspend fun getRoomDetail(roomId: Int): Result<ResponseDataInfo<RoomDetailInfo>>

    suspend fun deleteRoom(roomId: Int): Result<ResponseInfo>

    suspend fun searchRoom(code: Int): Result<ResponseDataInfo<SearchRoomInfo>>

    suspend fun getRoomList(hasNext: Boolean = true): Result<ResponseDataInfo<GetRoomListInfo>>
}
