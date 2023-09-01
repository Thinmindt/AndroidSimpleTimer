package com.example.simpletimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
class TimerRoom(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "duration") var duration: String,
) {
    // Override equals to compare TimerRoom instances based on their properties
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TimerRoom

        if (id != other.id) return false
        if (title != other.title) return false
        if (duration != other.duration) return false

        return true
    }
}