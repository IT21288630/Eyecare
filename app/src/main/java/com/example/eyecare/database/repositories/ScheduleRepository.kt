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

    suspend fun markAsTaken(scheduleID: Int) = db.getScheduleDao().markAsTaken(true, scheduleID)

    suspend fun updateScheduleItem(name: String, dose: String, time: String, medId: Int) = db.getScheduleDao().updateScheduleItem(name, dose, time, medId)

    suspend fun deleteScheduleItem(medId: Int) = db.getScheduleDao().deleteScheduleItem(medId)
}