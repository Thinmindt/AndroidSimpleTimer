package com.example.simpletimer

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpletimer.data.TimerDatabase
import com.example.simpletimer.data.TimerRoom
import com.example.simpletimer.data.TimerRoomDao
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerRoomDaoTest {

    private lateinit var database: TimerDatabase
    private lateinit var timerDao: TimerRoomDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TimerDatabase::class.java).build()
        timerDao = database.timerRoomDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveTimer() = runBlocking {
        val timer = TimerRoom(id = 1, title = "Test Timer", duration = "00:01:00")

        // Insert timer
        timerDao.addTimer(timer)

        // Retrieve timer and assert values
        val timers = timerDao.getAll()
        assert(timers.isNotEmpty())
        assert(timers[0] == timer)
    }

    @Test
    fun testDeleteTimer() = runBlocking {
        val timer = TimerRoom(id = 1, title = "Test Timer", duration = "00:01:00")

        // Insert timer
        timerDao.addTimer(timer)

        // Delete timer
        timerDao.delete(timer)

        // Retrieve timers and assert list is empty
        val timers = timerDao.getAll()
        assert(timers.isEmpty())
    }
}