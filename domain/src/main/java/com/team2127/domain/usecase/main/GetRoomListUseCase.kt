package com.team2127.domain.usecase.main

import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.main.GetRoomListInfo
import com.team2127.domain.repository.MainRepository
import javax.inject.Inject

class GetRoomListUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke():  Result<ResponseDataInfo<GetRoomListInfo>> =
        repository.getRoomList()
}
