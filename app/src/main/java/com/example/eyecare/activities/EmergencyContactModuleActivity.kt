package com.example.eyecare.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.eyecare.IS_DATALIST_NULL
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.EmergencyDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmergencyContactModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact_module)

        val addNewDetailBtn = findViewById<Button>(R.id.addNewEmergDetailBtn)
        val deleteDetailsBtn = findViewById<Button>(R.id.dltEmergDetailBtn)
        val updDetailBtn = findViewById<Button>(R.id.btnEmrgUpd)
        val emergencySetupIntent = Intent(this, EyeGuardianServiceActivity::class.java)

        refreshView()

        updDetailBtn.isEnabled = !IS_DATALIST_NULL.get()

        if(!updDetailBtn.isEnabled){
            updDetailBtn.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        }else{
            updDetailBtn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }

        updDetailBtn.setOnClickListener(){
            val updEmrgDetailIntent = Intent(this, UpdateEmergencyDetailsActivity::class.java)
            startActivity(updEmrgDetailIntent)
        }

        addNewDetailBtn.setOnClickListener(){
            startActivity(emergencySetupIntent)

            GlobalScope.launch(Dispatchers.IO){
                deleteAllDetails()
            }
        }

        deleteDetailsBtn.setOnClickListener(){
            GlobalScope.launch(Dispatchers.IO){
                deleteAllDetails()
            }
        }
    }

    fun refreshView(){
        var currentName = findViewById<TextView>(R.id.currentNameTxt)
        var currentContacts = findViewById<TextView>(R.id.currentEmergNoTxt)
        var currentMsg = findViewById<TextView>(R.id.currentEmergMsgTxt)

        GlobalScope.launch(Dispatchers.IO) {
            val dataList: List<EmergencyDetails> = getEmergencyDetails()
            IS_DATALIST_NULL.set(dataList.isNullOrEmpty())

            launch(Dispatchers.Main) {
                // Update UI on the main thread
                currentName.text = dataList.getOrNull(0)?.uName ?: "Emergency name is not available"
                currentContacts.text = (dataList.getOrNull(0)?.contNo1 ?: "Not available" )+ "\n" + (dataList.getOrNull(0)?.contNo2 ?: "Not available") + "\n" + (dataList.getOrNull(0)?.contNo3 ?: "Not available")
                currentMsg.text = dataList.getOrNull(0)?.emergMsg ?: "Emergency message is not available"
            }
        }
    }

    //Retrieving data from database
    suspend fun getEmergencyDetails(): List<EmergencyDetails> {

        val db = EyecareDatabase.getInstance(this@EmergencyContactModuleActivity)
        val emrgDao = db.getEmergencyDetails()

        return emrgDao.getAll()

    }

    suspend fun deleteAllDetails(){
        val db = EyecareDatabase.getInstance(this@EmergencyContactModuleActivity)
        val emrgDao = db.getEmergencyDetails()

        val toBeDeletedList = emrgDao.getAll()
        IS_DATALIST_NULL.set(true)
        if (toBeDeletedList.isNotEmpty()) {
            emrgDao.delete(toBeDeletedList[0])
            refreshView()
        }

    }
}