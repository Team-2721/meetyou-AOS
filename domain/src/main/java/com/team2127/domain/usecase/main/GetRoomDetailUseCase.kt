package com.team2127.domain.usecase.main

import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.main.RoomDetailInfo
import com.team2127.domain.repository.MainRepository
import javax.inject.Inject

class GetRoomDetailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(roomId: Int): Result<ResponseDataInfo<RoomDetailInfo>> =
        repository.getRoomDetail(roomId)
}
