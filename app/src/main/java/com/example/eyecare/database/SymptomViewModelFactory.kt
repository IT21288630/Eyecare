package com.example.eyecare.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eyecare.database.daos.ResultillnessDao


class SymptomViewModelFactory(private val symptomDao: ResultillnessDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SymptomViewModel::class.java)) {
            return SymptomViewModel(symptomDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
