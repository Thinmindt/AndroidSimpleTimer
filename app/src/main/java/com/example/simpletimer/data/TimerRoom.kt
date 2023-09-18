package com.example.simpletimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Objects

@Entity(tableName = "timers")
class TimerRoom(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "duration") var duration: String, // in Duration.toIsoString() format
) {
    // Override equals to compare TimerRoom instances based on their properties
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TimerRoom

        if (title != other.title) return false
        if (duration != other.duration) return false

        return true
    }

    override fun hashCode() = Objects.hash(id, title, duration)

    override fun toString(): String = "TimerRoom(id=$id, title=$title, duration=$duration)"

    fun copy(
        id: Long = this.id,
        title: String? = this.title,
        duration: String = this.duration,
    ): TimerRoom {
        return TimerRoom(id, title, duration)
    }
}