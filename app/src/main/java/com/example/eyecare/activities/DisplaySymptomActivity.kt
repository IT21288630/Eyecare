package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eyecare.R
//import com.example.eyecare.database.SympDatabase
import org.json.JSONArray
import com.example.eyecare.adapters.SymptomAdapter
import com.example.eyecare.database.SympDatabase
import com.example.eyecare.database.checkboxDatabase
import com.example.eyecare.database.entities.SymptomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//import com.example.eyecare.database.checkboxDatabase.Companion.MIGRATION_1_2

//import com.example.eyecare.database.daos.checked_sympDao
//import com.example.eyecare.database.entities.checked_symp

class DisplaySymptomActivity : AppCompatActivity() {

    //private lateinit var symptomViewModel: SymptomViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var btn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

       val db = Room.databaseBuilder(
            applicationContext,
            SympDatabase::class.java, "symptoms-database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val database = Room.databaseBuilder(
            this,
            checkboxDatabase::class.java, "SympDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()

        recyclerView = findViewById(R.id.recyclerView)
        symptomAdapter = SymptomAdapter(this, read_json(this),db,database)
        recyclerView.adapter = symptomAdapter



        backBtn.setOnClickListener {
            finish()
        }


        var results :Int= 0
        btn=findViewById(R.id.submit)

        btn.setOnClickListener {
            val intent = Intent(this, com.example.eyecare.activities.EyeTest_Result::class.java)
            startActivity(intent)
        }

        /*
        CoroutineScope(Dispatchers.IO).launch{
           results=database.checked_sympDao().checkedItems()
        }
        if(results!=0) (
                btn.setOnClickListener {
                    val intent = Intent(this, com.example.eyecare.activities.EyeTest_Result::class.java)
                    startActivity(intent)
                }
         )
        else{
            btn.setOnClickListener{
                showToast("Select Symptoms")
            }*/
        }







    }

    private fun read_json(context: Context): List<SymptomEntity> {
        val symptomsList = mutableListOf<SymptomEntity>()

        try {
            val json = context.assets.open("symptoms.json").bufferedReader().use {
                it.readText()
            }

            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val symptomObj = jsonArray.getJSONObject(i)
                val name = symptomObj.getString("name")
                symptomsList.add(SymptomEntity(name= name, isChecked = false))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return symptomsList
    }
    /*private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }*/


