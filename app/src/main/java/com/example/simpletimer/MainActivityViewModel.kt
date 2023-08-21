package com.example.simpletimer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.Duration

data class Timer(var name: MutableState<String>, val time: Duration)

class MainActivityViewModel : ViewModel() {
    val timerList = mutableStateListOf<Timer>(
        Timer(mutableStateOf("eggs"), Duration.ofMinutes(100)),
        Timer(mutableStateOf("spaghetti"), Duration.ofMinutes(11)),
    )

    fun deleteTimer(timer: Timer) {
        timerList.remove(timer)
    }


}