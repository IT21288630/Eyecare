package com.example.eyecare.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eyecare.database.entities.Medication

@Dao
interface MedicationDao {
    @Insert
    suspend fun insertMedication(medication: Medication)

    @Query("SELECT * From DailyPlan")
    fun getAllMedications(): List<Medication>
}