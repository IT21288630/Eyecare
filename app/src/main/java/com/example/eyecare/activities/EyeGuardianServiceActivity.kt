package com.example.eyecare.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eyecare.R
import com.example.eyecare.fragments.Fragment_getContactDetails
import com.example.eyecare.fragments.Fragment_CustomizeMsg
import com.example.eyecare.fragments.Fragment_getContactNumbers

class EyeGuardianServiceActivity : AppCompatActivity() {
    private lateinit var emrgName : String
    private lateinit var emrgNo_1 : String
    private lateinit var emrgNo_2 : String
    private lateinit var emrgNo_3 : String
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

    fun saveEmergName(paramName : String){
        emrgName = paramName
    }
    fun saveEmergNo(No1 : String,No2 : String,No3 : String){
        emrgNo_1 = No1
        emrgNo_2 = No2
        emrgNo_3 = No3
    }

    fun saveCustomMsg(msg : String){
        customMsg = msg
    }

    fun saveAllDetails(){

    }
}