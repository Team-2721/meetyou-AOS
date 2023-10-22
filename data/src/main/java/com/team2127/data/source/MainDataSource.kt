package com.team2127.data.source

import com.team2127.data.model.ResponseDataDTO
import com.team2127.data.model.main.GetRoomListDTO
import com.team2127.data.service.MainService
import javax.inject.Inject

class MainDataSource @Inject constructor(
    private val service: MainService
) {
    suspend fun getRoomList():ResponseDataDTO<GetRoomListDTO> =
        service.getRoomList()
}
