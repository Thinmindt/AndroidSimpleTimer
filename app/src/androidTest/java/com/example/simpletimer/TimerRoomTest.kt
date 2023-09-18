package com.example.simpletimer

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpletimer.data.TimerDatabase
import com.example.simpletimer.data.TimerRoom
import com.example.simpletimer.data.TimerRoomDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TimerRoomDaoTest {

    private lateinit var database: TimerDatabase
    private lateinit var timerDao: TimerRoomDao

    @Before
    fun setup() {
        // Create in-memory database, allowing queries on the main thread
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TimerDatabase::class.java
        ).allowMainThreadQueries().build()

        timerDao = database.timerRoomDao()
    }

    @After
    fun teardown() {
        // Close the database after tests finish
        database.close()
    }

    @Test
    fun testInsertAndRetrieveTimer() = runTest {
        val timer = TimerRoom(title = "Test Timer", duration = "00:01:00")

        // Insert timer
        timerDao.addTimer(timer)

        // Retrieve timer and assert values
        val timers = timerDao.getAll().first()

        assert(timers.size == 1)
        assert(timers[0] == timer)
    }

    @Test
    fun testUpdateTimer() = runTest {
        // Create a TimerRoom instance
        var timer = TimerRoom(title = "Test Timer", duration = "00:01:00")

        // Insert the timer into the database
        timerDao.addTimer(timer)

        // Replace our timer with the timer added to the database
        timer = timerDao.getAll().first()[0]

        // Update the timer's name
        val updatedTimer = timer.copy(title = "Updated Timer")
        timerDao.update(updatedTimer)

        // Retrieve the updated timer from the database
        val retrievedTimer = timerDao.getAll().first()[0]

        // Verify that the timer's name has been updated
        assertEquals(retrievedTimer.title, updatedTimer.title)
    }

    @Test
    fun testDeleteTimer() = runTest {
        var timer = TimerRoom(title = "Test Timer", duration = "00:01:00")

        // Insert timer
        timerDao.addTimer(timer)

        // Replace our timer with the timer added to the database
        timer = timerDao.getAll().first()[0]

        // Delete timer
        timerDao.delete(timer)

        // Retrieve timers and assert list is empty
        val timers = timerDao.getAll().first()
        assert(timers.isEmpty())
    }
}