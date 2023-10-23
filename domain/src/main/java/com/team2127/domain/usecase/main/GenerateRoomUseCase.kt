package com.team2127.domain.usecase.main

import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.main.GenerateRoomReqInfo
import com.team2127.domain.repository.MainRepository
import javax.inject.Inject

class GenerateRoomUseCase @Inject constructor(
    private val repository: MainRepository
){
    suspend operator fun invoke(generateRoomReqInfo: GenerateRoomReqInfo): Result<ResponseInfo> =
        repository.generateRoom(generateRoomReqInfo)
}
