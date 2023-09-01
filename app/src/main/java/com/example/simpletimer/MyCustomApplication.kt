package com.example.simpletimer

import android.app.Application
import androidx.room.Room
import com.example.simpletimer.data.TimerDatabase

class MyCustomApplication : Application() {
    // Database instance
    lateinit var database: TimerDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, TimerDatabase::class.java, "timer_database.db")
            .build()
    }
}