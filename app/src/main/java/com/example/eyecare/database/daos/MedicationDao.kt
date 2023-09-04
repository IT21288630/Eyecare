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

    @Query("DELETE From Medication")
    suspend fun deleteAllMedication()

    @Query("UPDATE Medication SET name = :name, dose = :dose, time = :time WHERE id = :id")
    suspend fun updateMedication(name: String, dose: String, time: Long, id: Int?)
}