package com.team2127.presentation.ui.main.generate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.model.main.GenerateRoomReqInfo
import com.team2127.domain.usecase.main.GenerateRoomUseCase
import com.team2127.presentation.ui.util.MutableEventFlow
import com.team2127.presentation.ui.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GenerateRoomViewModel @Inject constructor(
    private val generateRoomUseCase: GenerateRoomUseCase
): ViewModel() {

    val roomTitle = MutableStateFlow("")
    val peopleCount = MutableStateFlow("")
    val date = MutableStateFlow("")
    val memo = MutableStateFlow("")

    private val _startDate = MutableStateFlow(DEFAULT_DATE)
    val startDate : StateFlow<String> get() = _startDate

    private val _endDate = MutableStateFlow(DEFAULT_DATE)
    val endDate : StateFlow<String> get() = _endDate

    private val _peopleCountInt = MutableStateFlow(DEFAULT_PEOPLE_COUNT)
    val peopleCountInt: StateFlow<Int> get() = _peopleCountInt

    private val _spinnerEntry = MutableStateFlow(emptyList<String>())
    val spinnerEntry: StateFlow<List<String>> get() = _spinnerEntry

    private val _event = MutableEventFlow<Event>()
    val event = _event.asEventFlow()

    fun setSpinnerEntry(entry: List<String>){
        viewModelScope.launch {
            _spinnerEntry.emit(entry)
        }
    }

    fun peopleCountToInt(){
        viewModelScope.launch{
            val countSting = peopleCount.value
            val countInt = countSting.replace("명", "").toIntOrNull()?: DEFAULT_PEOPLE_COUNT

            _peopleCountInt.emit(countInt)
        }
    }

    fun setStartEndDate(date: androidx.core.util.Pair<Long, Long>){
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date.first ?: 0
            val start = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
            _startDate.emit(start)

            calendar.timeInMillis = date.second ?: 0
            val end = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
            _endDate.emit(end)
        }
    }

    fun generateRoom(){
        viewModelScope.launch {
            val roomName = roomTitle.value
            val attendeeNumber = peopleCountInt.value
            val startDate = startDate.value
            val endDate = endDate.value
            val comment = memo.value
            val result = generateRoomUseCase(GenerateRoomReqInfo(
                roomName,
                attendeeNumber,
                startDate,
                endDate,
                comment
            ))

            if (result.isSuccess){
                _event.emit(Event.SuccessGenerateRoom)
            } else {
                if (result.exceptionOrNull() == null){
                    _event.emit(Event.UnKnownException)
                } else{
                    _event.emit(Event.InvalidRequestException)
                }
            }
        }
    }

    sealed class Event{
        object SuccessGenerateRoom : Event()
        object InvalidRequestException: Event()
        object UnKnownException : Event()
    }

    companion object{
        private const val DEFAULT_PEOPLE_COUNT = 2
        private const val DEFAULT_DATE = "0000-00-00"
    }
}
