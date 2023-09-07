package com.example.eyecare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SymptomViewModelFactory(private val repository: SymptomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SymptomViewModel::class.java)) {
            return SymptomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
