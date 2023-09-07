package com.example.eyecare

import androidx.lifecycle.LiveData
import com.example.eyecare.database.daos.checked_sympDao
import com.example.eyecare.database.entities.checked_symp

class SymptomRepository(private val symptomDao: checked_sympDao) {

    fun getSymptoms(): List<String> {
        return symptomDao.getSymptoms()
    }

    fun insert(symptom: checked_symp) {

        symptomDao.insertCheckboxItem(symptom)

    }

}
