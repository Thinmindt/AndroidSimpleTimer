package com.example.simpletimer.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimerRoom::class], version = 1)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerRoomDao(): TimerRoomDao
}