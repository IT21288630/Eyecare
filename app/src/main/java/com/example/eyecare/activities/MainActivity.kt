package com.example.eyecare.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eyecare.R
import com.example.eyecare.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val floatBtn = findViewById<FloatingActionButton>(R.id.floatBtn)

        floatBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddMedicationActivity::class.java)
            startActivity(intent)
        }

        setCurrentFragment(ProfileFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                /*R.id.home -> {
                    setCurrentFragment(AddMedicationFragment())
                    Toast.makeText(this@MainActivity, "Home selected", Toast.LENGTH_LONG).show()
                }*/
                R.id.profile -> {
                    setCurrentFragment(ProfileFragment())
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}