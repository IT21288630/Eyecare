package com.example.eyecare.database.repositories

import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication

class MedicationRepository(private val db: EyecareDatabase) {

    suspend fun insertMedication(medication: Medication) = db.getMedicationDao().insertMedication(medication)

    fun getAllMedications() = db.getMedicationDao().getAllMedications()
}