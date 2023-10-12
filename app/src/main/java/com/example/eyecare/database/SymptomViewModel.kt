package com.example.eyecare.database

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eyecare.database.daos.ResultillnessDao
import com.example.eyecare.database.entities.ResultIllness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SymptomViewModel (private val symptomDao: ResultillnessDao) : ViewModel() {

    val allSymptoms: LiveData<List<ResultIllness>> = symptomDao.getAllSymptoms()


    fun delete(resultIllness: ResultIllness) {
        GlobalScope.launch(Dispatchers.IO) {
            // Perform the deletion operation on the database
            symptomDao.delete(resultIllness)


        }
    }



}