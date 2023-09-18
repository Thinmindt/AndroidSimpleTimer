package com.example.simpletimer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [TimerRoom::class], version = 1)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerRoomDao(): TimerRoomDao

    companion object {
        private var INSTANCE: TimerDatabase? = null

        fun getInstance(context: Context): TimerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): TimerDatabase {
            return databaseBuilder(
                context.applicationContext,
                TimerDatabase::class.java,
                "your_database_name"
            ).build()
        }
    }
}