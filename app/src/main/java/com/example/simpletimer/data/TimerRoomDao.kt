package com.example.simpletimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerRoomDao {
    @Query("SELECT * FROM timers")
    fun getAll(): Flow<List<TimerRoom>>

    @Delete
    suspend fun delete(timer: TimerRoom)

    @Insert
    suspend fun addTimer(timer: TimerRoom)

    @Update
    suspend fun update(updatedTimer: TimerRoom): Int
}