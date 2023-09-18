package com.example.simpletimer

import kotlin.time.Duration


fun Duration.toDisplayString(): String {
    val seconds = this.inWholeSeconds
    val hours = seconds / 3600 // number of seconds in an hour
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}
