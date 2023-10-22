package com.team2127.data.repository

import com.team2127.data.source.MainDataSource
import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.main.GetRoomListInfo
import com.team2127.domain.model.main.RoomResultInfo
import com.team2127.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val datasource: MainDataSource
): MainRepository {
    override suspend fun getRoomList(): Result<ResponseDataInfo<GetRoomListInfo>> {
        val result = runCatching{
            datasource.getRoomList()
        }

        return if(result.isSuccess){
            val roomList = result.getOrThrow()
            Result.success(ResponseDataInfo(
                roomList.isSuccess,
                roomList.detail,
                GetRoomListInfo(
                    roomList.data.result.map { RoomResultInfo(
                        it.roomId,
                        it.roomName,
                        it.attendeeNumber,
                        it.date,
                        it.code
                    ) },
                    roomList.data.count,
                    roomList.data.next,
                    roomList.data.previous
                )
            ))
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}
