package com.example.simpletimer.screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.TimerDatabase
import com.example.simpletimer.data.TimerRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Duration

class EnterTimeFormViewModel(application: Application) : AndroidViewModel(application) {
    // Hours, minutes, seconds in a single 6 digit string
    val hmsString = mutableStateOf("000000")

    private val timerDao = TimerDatabase.getInstance(application).timerRoomDao()

    fun enterNumber(number: String) {
        if (hmsString.value.substring(0, number.length).toInt() == 0)
            hmsString.value = hmsString.value.substring(number.length) + number
    }

    fun deleteNumber() {
        hmsString.value = "0" + hmsString.value.substring(0, hmsString.value.length - 1)
    }

    fun submitTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            val hours = hmsString.value.substring(0, 2)
            val minutes = hmsString.value.substring(2, 4)
            val seconds = hmsString.value.substring(4, 6)

            val defaultFormatDuration = "${hours}h ${minutes}m ${seconds}s"
            Log.i(TAG, "Submitted timer with duration: $defaultFormatDuration")
            val duration = Duration.parse(defaultFormatDuration)

            val newTimer = TimerRoom(duration = duration.toIsoString())
            timerDao.addTimer(newTimer)
        }
    }

    companion object {
        const val TAG = "EnterTimeFormViewModel"
    }
}