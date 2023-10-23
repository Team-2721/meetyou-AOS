package com.team2127.data.repository

import com.team2127.data.model.main.GenerateRoomRequestDTO
import com.team2127.data.source.MainDataSource
import com.team2127.domain.exception.ForbiddenException
import com.team2127.domain.exception.InternetServerErrorException
import com.team2127.domain.exception.InvalidRequestException
import com.team2127.domain.exception.NoMoreItemException
import com.team2127.domain.exception.NotExistRoomException
import com.team2127.domain.exception.UnKnownException
import com.team2127.domain.model.ResponseDataInfo
import com.team2127.domain.model.ResponseInfo
import com.team2127.domain.model.main.AttendeeInfo
import com.team2127.domain.model.main.GenerateRoomReqInfo
import com.team2127.domain.model.main.GetRoomListInfo
import com.team2127.domain.model.main.RoomDetailInfo
import com.team2127.domain.model.main.RoomResultInfo
import com.team2127.domain.model.main.SearchRoomInfo
import com.team2127.domain.repository.MainRepository
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_SERVER_ERROR
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val datasource: MainDataSource
): MainRepository {

    private val hasNextRoom = AtomicBoolean(true)
    override suspend fun generateRoom(generateRoomReqInfo: GenerateRoomReqInfo): Result<ResponseInfo> {
        val result = runCatching{datasource.generateRoom(generateRoomReqInfo.toGenerateRoomRequestDTO())}

        return if (result.isSuccess){
            Result.success(ResponseInfo(result.getOrThrow().isSuccess, result.getOrThrow().detail))
        } else {
            Result.failure(handleMainError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun getRoomDetail(roomId: Int): Result<ResponseDataInfo<RoomDetailInfo>> {
        val result = runCatching { datasource.getRoomDetail(roomId) }

        return if (result.isSuccess){
            val roomDetail = result.getOrThrow()
            Result.success(
                ResponseDataInfo(roomDetail.isSuccess, roomDetail.detail,
                    RoomDetailInfo(
                        roomDetail.data.roomId,
                        roomDetail.data.roomName,
                        roomDetail.data.attendeeNumber,
                        roomDetail.data.startDate,
                        roomDetail.data.endDate,
                        roomDetail.data.comment,
                        roomDetail.data.attendeeList.map{ AttendeeInfo(it.attendeeId, it.nickname, it.avatar, it.vote) },
                        roomDetail.data.result
                    ))
            )
        } else {
            Result.failure(handleMainError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun deleteRoom(roomId: Int): Result<ResponseInfo> {
        val result = runCatching { datasource.deleteRoom(roomId) }

        return if (result.isSuccess){
            Result.success(ResponseInfo(result.getOrThrow().isSuccess, result.getOrThrow().detail))
        } else {
            Result.failure(handleMainError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun searchRoom(code: Int): Result<ResponseDataInfo<SearchRoomInfo>> {

        val result = runCatching { datasource.searchRoom(code) }

        return if (result.isSuccess){
            val searchRoom = result.getOrThrow()
            Result.success(
                ResponseDataInfo(searchRoom.isSuccess, searchRoom.detail,
                SearchRoomInfo(
                    searchRoom.data.roomId,
                    searchRoom.data.roomName,
                    searchRoom.data.attendeeNumber,
                    searchRoom.data.startDate,
                    searchRoom.data.endDate,
                    searchRoom.data.comment,
                    searchRoom.data.attendeeList.map { AttendeeInfo(it.attendeeId, it.nickname, it.avatar, it.vote) }
                )
                )
            )
        } else {
            Result.failure(handleMainError(result.exceptionOrNull()!!))
        }
    }

    override suspend fun getRoomList(hasNext: Boolean): Result<ResponseDataInfo<GetRoomListInfo>> {
        hasNextRoom.set(hasNext)

        val result = runCatching{ datasource.getRoomList() }

        if (hasNextRoom.get().not()){
            return Result.failure(NoMoreItemException())
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
                        it.code,
                        it.status,
                        it.comment
                    ) },
                    roomList.data.count,
                    roomList.data.next,
                    roomList.data.previous
                )
            ))
        } else {
            Result.failure(handleMainError(result.exceptionOrNull()!!))
        }
    }

    private fun GenerateRoomReqInfo.toGenerateRoomRequestDTO(): GenerateRoomRequestDTO =
        GenerateRoomRequestDTO(
            roomName = roomName,
            attendeeNumber = attendeeNumber,
            startDate = startDate,
            endDate = endDate,
            comment = comment
        )

    private fun handleMainError(t: Throwable): Throwable{
        return if(t is HttpException){
            handleErrorCode(t.code())
        } else{
            t
        }
    }
    private fun handleErrorCode(code: Int): Throwable{
        return when (code){
            HTTP_BAD_REQUEST -> InvalidRequestException()
            HTTP_NOT_FOUND -> NotExistRoomException()
            HTTP_FORBIDDEN -> ForbiddenException()
            HTTP_SERVER_ERROR -> InternetServerErrorException()
            else -> UnKnownException()
        }
    }
}
