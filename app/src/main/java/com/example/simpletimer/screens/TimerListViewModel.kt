package com.example.simpletimer.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletimer.data.TimerDatabase
import com.example.simpletimer.data.TimerRoom
import com.example.simpletimer.toDisplayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration

class TimerListViewModel(application: Application) : AndroidViewModel(application) {
    private val timerDao = TimerDatabase.getInstance(application).timerRoomDao()
    val timers: StateFlow<List<TimerRoom>> =
        timerDao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteTimer(timer: TimerRoom) {
        viewModelScope.launch(Dispatchers.IO) {
            timerDao.delete(timer)
        }
    }

    fun updateTimer(updatedTimer: TimerRoom) {
        viewModelScope.launch(Dispatchers.IO) {
            timerDao.update(updatedTimer)
        }
    }

    fun getTimerDisplayString(timer: TimerRoom): String {
        val timerDuration = Duration.parseIsoString(timer.duration)
        return timerDuration.toDisplayString()
    }

    companion object {
        private const val TAG = "TimerListViewModel"
    }
}