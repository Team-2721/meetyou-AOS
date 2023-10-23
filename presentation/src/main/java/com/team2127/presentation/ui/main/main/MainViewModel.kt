package com.team2127.presentation.ui.main.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.exception.ForbiddenException
import com.team2127.domain.exception.InternetServerErrorException
import com.team2127.domain.exception.InvalidRequestException
import com.team2127.domain.exception.NotExistRoomException
import com.team2127.domain.exception.UnKnownException
import com.team2127.domain.model.main.GetRoomListInfo
import com.team2127.domain.model.main.RoomResultInfo
import com.team2127.domain.usecase.main.DeleteRoomUseCase
import com.team2127.domain.usecase.main.GetRoomListUseCase
import com.team2127.presentation.ui.login.login.LoginViewModel
import com.team2127.presentation.ui.util.MutableEventFlow
import com.team2127.presentation.ui.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRoomListUseCase: GetRoomListUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
): ViewModel() {

    private val _roomList = MutableStateFlow<List<RoomResultInfo>>(emptyList())
    val roomList: StateFlow<List<RoomResultInfo>> get() = _roomList

    private val _hasNextRoom = MutableStateFlow<Boolean>(true)

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    init {
        getRoomList()
    }

    private fun getRoomList(){
        viewModelScope.launch {
            val hasNext = _hasNextRoom.value
            val result = getRoomListUseCase(hasNext)

            if (result.isSuccess){
                val roomList = result.getOrThrow().data.result
                _roomList.emit(roomList)
                _event.emit(Event.SuccessGetRoomList)
            } else {
                handleError(result.exceptionOrNull()!!)
            }
        }
    }

    fun deleteRoom(roomId : Int){
        viewModelScope.launch {
            val result = deleteRoomUseCase(roomId)

            if (result.isSuccess){
                _event.emit(Event.SuccessDeleteRoomList)
            } else {
                handleError(result.exceptionOrNull()!!)
            }
        }
    }

    private suspend fun handleError(error:Throwable){
        when (error){
            is ForbiddenException -> _event.emit(Event.ForbiddenException)
            is InternetServerErrorException -> _event.emit(Event.InternetServerException)
            is InvalidRequestException -> _event.emit(Event.AlreadyDeleteRoomException)
            is NotExistRoomException -> _event.emit(Event.NotExistRoomException)
            is UnKnownException -> _event.emit(Event.UnKnownException)
        }
    }

    sealed class Event{
        object SuccessGetRoomList : Event()
        object SuccessDeleteRoomList : Event()
        object ForbiddenException : Event()
        object InternetServerException : Event()
        object NotExistRoomException : Event()
        object AlreadyDeleteRoomException : Event()
        object UnKnownException : Event()
    }
}
