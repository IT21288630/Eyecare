package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.eyecare.R

class EyeTest : AppCompatActivity() {

    private lateinit var btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eye_test)

        btn=findViewById(R.id.button)
        btn.setOnClickListener {
            val intent = Intent(this, Symptom::class.java)
            startActivity(intent)
        }
    }
}