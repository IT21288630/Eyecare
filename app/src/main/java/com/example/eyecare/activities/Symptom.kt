package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eyecare.R
//import com.example.eyecare.database.SympDatabase
import org.json.JSONArray
import com.example.eyecare.Symptom
import com.example.eyecare.adapters.SymptomAdapter
import com.example.eyecare.SymptomViewModel

import com.example.eyecare.database.SympDatabase
import com.example.eyecare.database.checkboxDatabase

//import com.example.eyecare.database.daos.checked_sympDao
//import com.example.eyecare.database.entities.checked_symp

class Symptom : AppCompatActivity() {


    private lateinit var symptomViewModel: SymptomViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var btn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom)

        val db = Room.databaseBuilder(
            applicationContext,
            SympDatabase::class.java, "symptoms-database"
        ).build()

        val database = Room.databaseBuilder(
            this,
            checkboxDatabase::class.java, "checkedDatabase"
        ).build()

        recyclerView = findViewById(R.id.recyclerView)
        symptomAdapter = SymptomAdapter(this, read_json(this),database)
        recyclerView.adapter = symptomAdapter





        btn=findViewById(R.id.submit)
        btn.setOnClickListener {
            val intent = Intent(this, com.example.eyecare.activities.Test_Result::class.java)
            startActivity(intent)
        }



    }

    private fun read_json(context: Context): List<Symptom> {
        val symptomsList = mutableListOf<Symptom>()

        try {
            val json = context.assets.open("symptoms.json").bufferedReader().use {
                it.readText()
            }

            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val symptomObj = jsonArray.getJSONObject(i)
                val name = symptomObj.getString("name")
                symptomsList.add(Symptom(name))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return symptomsList
    }


}