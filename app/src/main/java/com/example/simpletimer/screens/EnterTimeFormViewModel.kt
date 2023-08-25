package com.example.simpletimer.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EnterTimeFormViewModel : ViewModel() {
    // Hours, minutes, seconds in a single 6 digit string
    val hmsString = mutableStateOf("000000")

    fun enterNumber(number: String) {
        if (hmsString.value.substring(0, number.length).toInt() == 0)
            hmsString.value = hmsString.value.substring(number.length) + number
    }

    fun deleteNumber() {
        hmsString.value = "0" + hmsString.value.substring(0, hmsString.value.length - 1)
    }

    fun submitTimer() {
        TODO("Not yet implemented")
    }
}