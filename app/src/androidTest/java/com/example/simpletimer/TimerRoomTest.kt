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
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TimerDatabase::class.java
        ).build()
        timerDao = database.timerRoomDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveTimer() {
        val timer = TimerRoom(id = 1, title = "Test Timer", duration = "00:01:00")

        val testScheduler = TestScheduler()

        // Insert timer
        val addTestObserver = timerDao.addTimer(timer).subscribeOn(testScheduler).test()
        addTestObserver.assertComplete()

        // Advance the scheduler to trigger the completion of addTimer
        testScheduler.triggerActions()

        // Retrieve timer and assert values
        val getAllTestObserver = timerDao.getAll().subscribeOn(testScheduler).test()
        testScheduler.triggerActions()

        getAllTestObserver.assertValue { timers ->
            timers.isNotEmpty() && timers[0] == timer
        }
    }

    @Test
    fun testDeleteTimer() {
        val timer = TimerRoom(id = 1, title = "Test Timer", duration = "00:01:00")

        // Insert timer
        timerDao.addTimer(timer).test().assertComplete()

        // Delete timer
        timerDao.delete(timer).test().assertComplete()

        // Retrieve timers and assert list is empty
        timerDao.getAll().test()
            .await()
            .assertComplete()
            .assertValue{ timers ->
                Log.i("test", "value = $timers")
                timers.isEmpty()
            }
    }
}