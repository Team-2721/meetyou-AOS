package com.team2127.data.source

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.ResponseDataDTO
import com.team2127.data.model.main.GenerateRoomRequestDTO
import com.team2127.data.model.main.GetRoomDetailDTO
import com.team2127.data.model.main.GetRoomListDTO
import com.team2127.data.model.main.SearchRoomInfoDTO
import com.team2127.data.service.MainService
import javax.inject.Inject

class MainDataSource @Inject constructor(
    private val service: MainService
) {
    suspend fun generateRoom(generateRoomRequestDTO: GenerateRoomRequestDTO): ResponseDTO =
        service.generateRoom(generateRoomRequestDTO)

    suspend fun getRoomDetail(roomId: Int): ResponseDataDTO<GetRoomDetailDTO> =
        service.getRoomDetail(roomId)

    suspend fun deleteRoom(roomId: Int): ResponseDTO =
        service.deleteRoom(roomId)

    suspend fun searchRoom(code: Int): ResponseDataDTO<SearchRoomInfoDTO> =
        service.searchRoom(code)

    suspend fun getRoomList():ResponseDataDTO<GetRoomListDTO> =
        service.getRoomList()
}
