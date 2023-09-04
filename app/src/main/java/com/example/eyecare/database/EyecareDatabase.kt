package com.example.eyecare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eyecare.database.daos.MedicationDao
import com.example.eyecare.database.daos.ScheduleDao
import com.example.eyecare.database.entities.EmergencyDetails
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.entities.Schedule

@Database(entities = [Medication::class, EmergencyDetails::class, Schedule::class], version = 1)
abstract class EyecareDatabase : RoomDatabase() {

    abstract fun getMedicationDao(): MedicationDao
    abstract fun getScheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: EyecareDatabase? = null

        fun getInstance(context: Context): EyecareDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EyecareDatabase::class.java,
                    "eyecare_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}