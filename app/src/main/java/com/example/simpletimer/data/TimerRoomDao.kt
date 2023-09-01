package com.example.simpletimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface TimerRoomDao {
    @Query("SELECT * FROM timers")
    fun getAll(): Observable<List<TimerRoom>>

    @Delete
    fun delete(timer: TimerRoom): Single<Int>

    @Insert
    fun addTimer(timer: TimerRoom): Completable
}