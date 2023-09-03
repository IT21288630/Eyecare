package com.example.eyecare.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eyecare.database.entities.Medication

@Dao
interface MedicationDao {
    @Insert
    suspend fun insertMedication(medication: Medication)

    @Query("SELECT * From Medication")
    fun getAllMedications(): List<Medication>

    @Delete
    suspend fun deleteMedication(medication: Medication)
}