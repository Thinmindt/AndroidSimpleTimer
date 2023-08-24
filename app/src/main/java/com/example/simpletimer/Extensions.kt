package com.example.simpletimer

import java.time.Duration


fun Duration.toDisplayString(): String {
    return "${this.toHours()}:${this.toMinutes() % 60}:${seconds % 60}"
}
