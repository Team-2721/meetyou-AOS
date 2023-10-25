package com.team2127.presentation.ui.main.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateRoomViewModel @Inject constructor(

): ViewModel() {

    val roomTitle = MutableStateFlow<String>("")
    val peopleCount = MutableStateFlow<String>("")
    val date = MutableStateFlow<String>("")
    val memo = MutableStateFlow<String>("")

    private val _spinnerEntry = MutableStateFlow(emptyList<String>())
    val spinnerEntry: StateFlow<List<String>> get() = _spinnerEntry

    fun setSpinnerEntry(entry: List<String>){
        viewModelScope.launch {
            _spinnerEntry.emit(entry)
        }
    }

    companion object{
        private const val default_people_count = 2
    }
}
