package com.example.eyecare.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.eyecare.R
import com.example.eyecare.activities.AddMedicationActivity
import com.example.eyecare.activities.DisplaySymptomActivity
import com.example.eyecare.activities.EyeTrackerMainActivity
import com.example.eyecare.activities.TestResultList
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Schedule
import com.example.eyecare.database.repositories.MedicationRepository
import com.example.eyecare.database.repositories.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartCard = view.findViewById<CardView>(R.id.chartCard)
        val viewMedCard = view.findViewById<CardView>(R.id.viewMedCard)
        val viewChartBtn = view.findViewById<Button>(R.id.viewChartBtn)
        val viewMedBtn = view.findViewById<Button>(R.id.viewMedBtn)
        val addMedBtn = view.findViewById<Button>(R.id.addMedBtn)
        val viewScheduleCard = view.findViewById<CardView>(R.id.viewScheduleCard)
        val viewScheduleBtn = view.findViewById<Button>(R.id.viewScheduleBtn)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val tvProgress = view.findViewById<TextView>(R.id.tvProgress)
        val sympcheck = view.findViewById<Button>(R.id.SymtomChecker)
        val symptestResult = view.findViewById<Button>(R.id.view_testResults)

        val scheduleRepository = ScheduleRepository(EyecareDatabase.getInstance(view.context))

        CoroutineScope(Dispatchers.IO).launch {
            var total = scheduleRepository.getScheduleAll(LocalDate.now().toString()).size
            var taken = scheduleRepository.getScheduleTaken(LocalDate.now().toString()).size

            withContext(Dispatchers.Main) {
                tvProgress.text = "$taken/$total"

                var progress = taken.toDouble() / total * 100
                println("Progress = $progress")
                progressBar.progress = progress.toInt()

            }
        }

        chartCard.setOnClickListener {
            val chartFragment = ChartFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(((view as ViewGroup).parent as View).id, chartFragment, "profileFragment")
                .addToBackStack(null)
                .commit()
        }

        viewChartBtn.setOnClickListener {
            val chartFragment = ChartFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(((view as ViewGroup).parent as View).id, chartFragment, "profileFragment")
                .addToBackStack(null)
                .commit()
        }

        viewMedCard.setOnClickListener {
            val viewMedicationFragment = ViewMedicationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    ((view as ViewGroup).parent as View).id,
                    viewMedicationFragment,
                    "profileFragment"
                )
                .addToBackStack(null)
                .commit()
        }

        viewMedBtn.setOnClickListener {
            val viewMedicationFragment = ViewMedicationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    ((view as ViewGroup).parent as View).id,
                    viewMedicationFragment,
                    "profileFragment"
                )
                .addToBackStack(null)
                .commit()
        }

        addMedBtn.setOnClickListener {
            val intent = Intent(view.context, AddMedicationActivity::class.java)
            startActivity(intent)
        }

        viewScheduleBtn.setOnClickListener {
            val todayScheduleFragment = TodayScheduleFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    ((view as ViewGroup).parent as View).id,
                    todayScheduleFragment,
                    "profileFragment"
                )
                .addToBackStack(null)
                .commit()
        }

        viewScheduleCard.setOnClickListener {
            val todayScheduleFragment = TodayScheduleFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    ((view as ViewGroup).parent as View).id,
                    todayScheduleFragment,
                    "profileFragment"
                )
                .addToBackStack(null)
                .commit()
        }
        sympcheck.setOnClickListener {
            val intent = Intent(view.context, EyeTrackerMainActivity::class.java)
            startActivity(intent)
        }

        symptestResult.setOnClickListener {
            val intent = Intent(view.context, TestResultList::class.java)
            startActivity(intent)
        }
    }
}