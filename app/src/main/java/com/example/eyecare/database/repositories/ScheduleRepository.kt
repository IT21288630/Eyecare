package com.example.eyecare.database.repositories

import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.entities.Schedule

class ScheduleRepository(private val db: EyecareDatabase) {

    suspend fun insertToSchedule(schedule: Schedule) =
        db.getScheduleDao().insertToSchedule(schedule)

    suspend fun insertToScheduleAfter(schedule: Schedule) =
        db.getScheduleDao().insertToScheduleAfter(schedule)

    fun getSchedule(date: String) = db.getScheduleDao().getSchedule(date, false)

    fun getScheduleAll(date: String) = db.getScheduleDao().getScheduleAll(date)

    fun getScheduleForChart() = db.getScheduleDao().getScheduleForChart()

    fun getScheduleTaken(date: String) = db.getScheduleDao().getScheduleTaken(date, true)

    suspend fun markAsTaken(scheduleID: Int) = db.getScheduleDao().markAsTaken(true, scheduleID)

    suspend fun updateScheduleItem(name: String, dose: String, time: String, medId: Int) = db.getScheduleDao().updateScheduleItem(name, dose, time, medId)

    suspend fun deleteScheduleItem(medId: Int) = db.getScheduleDao().deleteScheduleItem(medId)

    suspend fun deleteAllScheduleItems() = db.getScheduleDao().deleteAllScheduleItems()
}