package com.team2127.domain.usecase.main

import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.main.SearchRoomInfo
import com.team2127.domain.repository.MainRepository
import javax.inject.Inject

class SearchRoomUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(code: Int): Result<ResponseDataInfo<SearchRoomInfo>> =
        repository.searchRoom(code)
}
