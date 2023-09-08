package com.example.eyecare.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.entities.Schedule

@Dao
interface ScheduleDao {

    @Insert
    suspend fun insertToSchedule(schedule: Schedule)

    @Insert
    suspend fun insertToScheduleAfter(schedule: Schedule)

    @Query("SELECT * From Schedule WHERE date = :date AND isTaken = :isTaken")
    fun getSchedule(date: String, isTaken: Boolean): List<Schedule>

    @Query("SELECT * From Schedule WHERE date = :date")
    fun getScheduleAll(date: String): List<Schedule>

    @Query("SELECT * From Schedule WHERE date = :date AND isTaken = :isTaken")
    fun getScheduleTaken(date: String, isTaken: Boolean): List<Schedule>

    @Query("UPDATE Schedule SET isTaken = :isTaken WHERE scheduleId = :scheduleId")
    suspend fun markAsTaken(isTaken: Boolean, scheduleId: Int?)

    @Query("UPDATE Schedule SET name = :name, dose = :dose, time = :time WHERE medId = :medId")
    suspend fun updateScheduleItem(name: String, dose: String, time: String, medId: Int)

    @Query("DELETE FROM Schedule WHERE medId = :medId")
    suspend fun deleteScheduleItem(medId: Int)

    @Query("DELETE From Schedule")
    suspend fun deleteAllScheduleItems()
}