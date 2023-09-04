package com.example.eyecare.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eyecare.database.entities.Schedule

@Dao
interface ScheduleDao {

    @Insert
    suspend fun insertToSchedule(schedule: Schedule)

    @Insert
    suspend fun insertToScheduleAfter(schedule: Schedule)

    @Query("SELECT * From Schedule WHERE date = :date AND isTaken = :isTaken")
    fun getSchedule(date: String, isTaken: Boolean): List<Schedule>

    @Query("UPDATE Schedule SET isTaken = :isTaken WHERE scheduleId = :scheduleId")
    suspend fun markAsTaken(isTaken: Boolean, scheduleId: Int?)
}