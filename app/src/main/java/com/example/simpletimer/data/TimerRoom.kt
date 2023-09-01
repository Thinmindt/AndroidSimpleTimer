package com.example.simpletimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
class TimerRoom(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "duration") var duration: String,
)