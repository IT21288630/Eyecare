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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EmergencyContactModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact_module)

        val addNewDetailBtn = findViewById<Button>(R.id.addNewEmergDetailBtn)
        val deleteDetailsBtn = findViewById<Button>(R.id.dltEmergDetailBtn)

        val emergencySetupIntent = Intent(this, EyeGuardianServiceActivity::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            refreshView()
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

    fun updBtnAct(){
        val updDetailBtn = findViewById<Button>(R.id.btnEmrgUpd)
        updDetailBtn.isEnabled = !IS_DATALIST_NULL.get()

        if(!updDetailBtn.isEnabled){
            updDetailBtn.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        }else{
            updDetailBtn.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }

        updDetailBtn.setOnClickListener(){
            val updEmrgDetailIntent = Intent(this@EmergencyContactModuleActivity, UpdateEmergencyDetailsActivity::class.java)
            startActivity(updEmrgDetailIntent)
        }
    }
    suspend fun refreshView(){
        var currentName = findViewById<TextView>(R.id.currentNameTxt)
        var currentContacts = findViewById<TextView>(R.id.currentEmergNoTxt)
        var currentMsg = findViewById<TextView>(R.id.currentEmergMsgTxt)

        coroutineScope {
            val dataList: List<EmergencyDetails> = getEmergencyDetails()
            IS_DATALIST_NULL.set(dataList.isNullOrEmpty())



            launch(Dispatchers.Main) {
                // Update UI on the main thread
                updBtnAct()
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