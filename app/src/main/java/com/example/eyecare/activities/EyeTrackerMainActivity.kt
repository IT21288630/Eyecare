package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eyecare.R

class EyeTrackerMainActivity : AppCompatActivity() {

    private lateinit var btn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eye_tracker_main)


        btn=findViewById(R.id.Startbutton)
        btn.setOnClickListener {
            val intent = Intent(this, DisplaySymptomActivity::class.java)
            startActivity(intent)
        }
    }
}