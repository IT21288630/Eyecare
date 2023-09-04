package com.example.eyecare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eyecare.R

class EmergencyContactModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contact_module)

        val addNewDetailBtn = findViewById<Button>(R.id.addNewEmergDetailBtn)
        val emergencySetupIntent = Intent(this, EyeGuardianServiceActivity::class.java)
        addNewDetailBtn.setOnClickListener(){
            startActivity(emergencySetupIntent)
        }
    }




}