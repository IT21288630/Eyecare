package com.example.eyecare.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.eyecare.R
import com.example.eyecare.TodayScheduleFragment
import com.example.eyecare.activities.AddMedicationActivity


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
                .replace(((view as ViewGroup).parent as View).id, viewMedicationFragment, "profileFragment")
                .addToBackStack(null)
                .commit()
        }

        viewMedBtn.setOnClickListener {
            val viewMedicationFragment = ViewMedicationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(((view as ViewGroup).parent as View).id, viewMedicationFragment, "profileFragment")
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
                .replace(((view as ViewGroup).parent as View).id, todayScheduleFragment, "profileFragment")
                .addToBackStack(null)
                .commit()
        }

        viewScheduleCard.setOnClickListener {
            val todayScheduleFragment = TodayScheduleFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(((view as ViewGroup).parent as View).id, todayScheduleFragment, "profileFragment")
                .addToBackStack(null)
                .commit()
        }
    }
}