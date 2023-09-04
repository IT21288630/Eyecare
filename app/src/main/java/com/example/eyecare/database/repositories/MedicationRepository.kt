package com.example.eyecare.database.repositories

import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication

class MedicationRepository(private val db: EyecareDatabase) {

    suspend fun insertMedication(medication: Medication) = db.getMedicationDao().insertMedication(medication)

    fun getAllMedications() = db.getMedicationDao().getAllMedications()

    suspend fun deleteMedication(medication: Medication) = db.getMedicationDao().deleteMedication(medication)

    suspend fun deleteAllMedication() = db.getMedicationDao().deleteAllMedication()

    suspend fun updateMedication(name: String, dose: String, time: Long, id: Int?) = db.getMedicationDao().updateMedication(name, dose, time, id)
}