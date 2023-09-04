package com.example.eyecare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.EmergencyDetails

class EmergencyContactModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact_module)

        val addNewDetailBtn = findViewById<Button>(R.id.addNewEmergDetailBtn)
        val emergencySetupIntent = Intent(this, EyeGuardianServiceActivity::class.java)

        var currentName = findViewById<TextView>(R.id.currentNameTxt)
        var currentContacts = findViewById<TextView>(R.id.currentEmergNoTxt)
        var currentMsg = findViewById<TextView>(R.id.currentEmergMsgTxt)

        val dataList : List<EmergencyDetails> = getEmergencyDetails()

        //Setting current emergency values
        currentName.text = dataList.getOrNull(0)?.uName ?: "Emergency name is not available"
        currentContacts.text = dataList.getOrNull(0)?.contNo1 ?: "Emergency Contact number 1 is not available" +"\n"+dataList.getOrNull(0)?.contNo2 ?: "Emergency contact number 2 is not available"+"\n"+dataList.getOrNull(0)?.contNo3 ?: "Emergency contact number 3 is not available"
        currentMsg.text = dataList.getOrNull(0)?.emergMsg ?: "Emergency message is not available"

        addNewDetailBtn.setOnClickListener(){
            startActivity(emergencySetupIntent)
        }
    }


    //Retrieving data from database
    fun getEmergencyDetails(): List<EmergencyDetails> {

        val db = EyecareDatabase.getInstance(this@EmergencyContactModuleActivity)
        val emrgDao = db.getEmergencyDetails()

        return emrgDao.getAll()

    }
}