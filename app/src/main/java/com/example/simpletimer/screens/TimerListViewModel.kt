package com.example.simpletimer.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.Duration

data class Timer(
    val name: MutableState<String>,
    val time: Duration,
    val editingName: MutableState<Boolean> = mutableStateOf(false)
)

class TimerListViewModel : ViewModel() {
    val timersList = mutableStateListOf<Timer>(
        Timer(mutableStateOf("eggs"), Duration.ofMinutes(100)),
        Timer(mutableStateOf("spaghetti"), Duration.ofMinutes(11))
    )

    fun deleteTimer(timer: Timer) {
        Log.i(TAG, "Delete called")
        timersList.remove(timer)
    }

    fun addTimer() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "TimerListViewModel"
    }
}