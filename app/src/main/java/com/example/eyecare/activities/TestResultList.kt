package com.example.eyecare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eyecare.R
import com.example.eyecare.adapters.ResultAdapter
import com.example.eyecare.adapters.SymptomAdapter
import com.example.eyecare.database.SympDatabase
import com.example.eyecare.database.SymptomViewModel
import com.example.eyecare.database.SymptomViewModelFactory
import com.example.eyecare.database.daos.ResultillnessDao
import com.example.eyecare.database.entities.ResultIllness

class TestResultList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var symptomAdapter: ResultAdapter
    private lateinit var symptomViewModel: SymptomViewModel
    private lateinit var db: SympDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result_list)

        val backBtn=findViewById<ImageView>(R.id.backBtn)

        db = Room.databaseBuilder(
            applicationContext,
            SympDatabase::class.java, "symptoms-database"
        ).build()


        recyclerView = findViewById(R.id.recyclerView)
        symptomAdapter = ResultAdapter(::onDeleteItemClick)
        recyclerView.adapter = symptomAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //symptomViewModel = ViewModelProvider(this)[SymptomViewModel::class.java]

        backBtn.setOnClickListener {
            finish()
        }


        val sympDao= db.ResultillnessDao()
        symptomViewModel = ViewModelProvider(this, SymptomViewModelFactory(sympDao)).get(SymptomViewModel::class.java)

        symptomViewModel.allSymptoms.observe(this, Observer { symptoms ->
            symptomAdapter.setSymptoms(symptoms)
        })

    }
    private fun onDeleteItemClick(resultIllness: ResultIllness) {
        // Handle item deletion here
        // You can remove the item from the database and update the UI
        symptomViewModel.delete(resultIllness)

        showToast("Test result deleted")

    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}
