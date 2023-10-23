package com.team2127.data.service

import com.team2127.data.model.ResponseDTO
import com.team2127.data.model.ResponseDataDTO
import com.team2127.data.model.main.GenerateRoomRequestDTO
import com.team2127.data.model.main.GetRoomDetailDTO
import com.team2127.data.model.main.GetRoomListDTO
import com.team2127.data.model.main.SearchRoomInfoDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    @POST("rooms/")
    suspend fun generateRoom(
        @Body generateRoomRequestDTO: GenerateRoomRequestDTO
    ): ResponseDTO

    @GET("rooms/{pk}")
    suspend fun getRoomDetail(
        @Path("pk") roomId: Int
    ): ResponseDataDTO<GetRoomDetailDTO>

    @DELETE("rooms/{pk}")
    suspend fun deleteRoom(
        @Path("pk") roomId: Int
    ): ResponseDTO

    @GET("rooms/search")
    suspend fun searchRoom(
        @Body code: Int
    ): ResponseDataDTO<SearchRoomInfoDTO>

    @GET("rooms/")
    suspend fun getRoomList(
        @Query("page") page: Int? = 1,
        @Query("page_size") pageSize : Int? = 10
    ): ResponseDataDTO<GetRoomListDTO>
}
