package com.example.eyecare.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.eyecare.R
import com.example.eyecare.fragments.Fragment_getContactDetails
import com.example.eyecare.fragments.Fragment_CustomizeMsg
import com.example.eyecare.fragments.Fragment_getContactNumbers

class EyeGuardianServiceActivity : AppCompatActivity() {
    private lateinit var emrgName : String
    private lateinit var emrgNo1 : String
    private lateinit var emrgNo2 : String
    private lateinit var emrgNo3 : String
    private lateinit var customMsg : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eye_guardian_service)



        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,Fragment_getContactDetails())
            commit()
        }


    }

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
      //  Toast.makeText(this, "Details saved!", Toast.LENGTH_LONG).show()
        val emergHomeIntent = Intent(this,EmergencyContactModuleActivity::class.java)
        startActivity(emergHomeIntent)
    }

    fun saveEmergName(paramName : String){
        emrgName = paramName
    }
    fun saveEmergNo(No1 : String,No2 : String,No3 : String){
        emrgNo1 = No1
        emrgNo2 = No2
        emrgNo3 = No3
    }

    fun saveCustomMsg(msg : String){
        customMsg = msg
         saveAllDetails()
    }

    fun saveAllDetails(){
        Log.i("Print value" , "\" $emrgName + $emrgNo1 + $emrgNo2 + $emrgNo3 +$customMsg\"")

    }
}