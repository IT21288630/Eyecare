package com.example.eyecare.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.eyecare.IS_DATALIST_NULL
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.EmergencyDetails
import com.example.eyecare.fragments.Fragment_getContactDetails
import com.example.eyecare.fragments.Fragment_CustomizeMsg
import com.example.eyecare.fragments.Fragment_getContactNumbers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EyeGuardianServiceActivity : AppCompatActivity() {
    private val emrgName : String = "Not assigned"
    private val emrgNo1 : String = "Not assigned"
    private val emrgNo2 : String = "Not assigned"
    private val emrgNo3 : String = "Not assigned"
    private val customMsg : String = "Not assigned"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eye_guardian_service)



        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,Fragment_getContactDetails())
            commit()
        }


    }
    var EmergEntity = EmergencyDetails(id = 0,emrgName,emrgNo1,emrgNo2,emrgNo3,customMsg)
    fun callContNumberFrag(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,Fragment_getContactNumbers())
            commit()
        }
    }
    fun callCustomMsgFrag(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,Fragment_CustomizeMsg())
            commit()
        }
    }

    fun callEmergHome(){

        val emergHomeIntent = Intent(this,EmergencyContactModuleActivity::class.java)
        startActivity(emergHomeIntent)
    }

    fun saveEmergName(paramName : String){
        EmergEntity.uName = paramName
    }
    fun saveEmergNo(No1 : String,No2 : String,No3 : String){
        EmergEntity.contNo1 = No1
        EmergEntity.contNo2 = No2
        EmergEntity.contNo3 = No3
    }

    fun saveCustomMsg(msg : String){
        EmergEntity.emergMsg = msg
         saveAllDetails()
    }

    fun saveAllDetails() = CoroutineScope(Dispatchers.IO).launch {

        withContext(Dispatchers.IO) {
            IS_DATALIST_NULL.set(false)
            val db = EyecareDatabase.getInstance(this@EyeGuardianServiceActivity)
            val emrgDao = db.getEmergencyDetails()

            emrgDao.insert(EmergEntity)
            Toast.makeText(this@EyeGuardianServiceActivity, "Details saved!", Toast.LENGTH_LONG).show()
            }
        }
}