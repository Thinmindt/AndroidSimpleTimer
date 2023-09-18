package com.example.simpletimer

import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ExtensionsTest {
    @Test
    fun testToFormattedString() {
        // Test cases
        val testCases = mapOf(
            2.hours + 30.minutes + 15.seconds to "02:30:15",
            0.hours + 45.minutes + 5.seconds to "00:45:05",
            10.hours + 0.minutes + 0.seconds to "10:00:00"
        )

        for ((duration, expected) in testCases) {
            val formattedString = duration.toDisplayString()
            assertEquals(expected, formattedString)
        }
    }
}