package com.team2127.presentation.ui.main.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2127.domain.usecase.main.GetRoomListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getRoomListUseCase: GetRoomListUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            val result = getRoomListUseCase()

            if (result.isSuccess){
                Log.d("getRoom", "Success")
            } else {
                Log.d("getRoom", "Fail : ${result.exceptionOrNull()?.message}")
            }
        }
    }
}
