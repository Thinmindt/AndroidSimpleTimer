package com.example.simpletimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TimerRoomDao {
    @Query("SELECT * FROM timers")
    suspend fun getAll(): List<TimerRoom>

    @Delete
    suspend fun delete(timer: TimerRoom)

    @Insert
    suspend fun addTimer(timer: TimerRoom)
}