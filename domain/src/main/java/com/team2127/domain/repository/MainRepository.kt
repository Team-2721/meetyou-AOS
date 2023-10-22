package com.team2127.domain.repository

import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.main.GetRoomListInfo


interface MainRepository {
    suspend fun getRoomList(): Result<ResponseDataInfo<GetRoomListInfo>>
}
