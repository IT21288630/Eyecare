package com.example.eyecare.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.eyecare.*
import com.example.eyecare.database.checkboxDatabase
import com.example.eyecare.database.daos.checked_sympDao

class Test_Result : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var illnessChecker: IllnessChecker
    private lateinit var symptomDao: checked_sympDao
    //private lateinit var symptoms: LiveData<List<String>>
    private lateinit var symptomViewModel: SymptomViewModel
    private lateinit var repository: SymptomRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_result)





        symptomDao = checkboxDatabase.getDatabase(this).checked_sympDao()
        //symptoms = symptomDao.getSymptoms()
        illnessChecker = IllnessChecker(this)

        repository =SymptomRepository(symptomDao)
        symptomViewModel = ViewModelProvider(this, SymptomViewModelFactory(repository))[SymptomViewModel::class.java]


        textView=findViewById(R.id.result)

        getResult()



    }



    @SuppressLint("SetTextI18n")
    private fun getResult() {

        val storedSymptomsLiveData = symptomViewModel.allSymptoms
        
        val mostSuitableIllness = illnessChecker.findMostSuitableIllness(storedSymptomsLiveData)

        if (mostSuitableIllness != null) {
            textView.text = "Most Suitable Illness: ${mostSuitableIllness.name}"
        } else {
            textView.text = "No suitable illness found."
        }

    }
}