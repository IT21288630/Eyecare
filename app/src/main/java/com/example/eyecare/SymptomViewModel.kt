package com.example.eyecare



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eyecare.database.entities.checked_symp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SymptomViewModel(private val repository: SymptomRepository) : ViewModel() {

    val allSymptoms: List<String> = repository.getSymptoms()

    fun insert(symptom: checked_symp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(symptom)
        }
    }
}