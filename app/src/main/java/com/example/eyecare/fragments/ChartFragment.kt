package com.example.eyecare.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import com.db.williamchart.view.BarChartView
import com.db.williamchart.view.LineChartView
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.repositories.MedicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChartFragment : Fragment(R.layout.fragment_chart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val medicationList = view.findViewById<AutoCompleteTextView>(R.id.medicationList)

        val medicationRepository = MedicationRepository(EyecareDatabase.getInstance(view.context))
        var medications = listOf<Medication>()
        var names = mutableListOf<String>()

        CoroutineScope(Dispatchers.IO).launch {
            medications = medicationRepository.getAllMedications()

            for (medication in medications) {
                names.add(medication.name)
            }
        }

        val adapter = ArrayAdapter(
            view.context,
            R.layout.dropdown_item,
            names
        )

        medicationList.setAdapter(adapter)

        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }

        val dataSet = listOf(
            "Mon" to 10f, 
            "Tue" to 5f, 
            "Wed" to 8f,
            "Thu" to 1f,
            "Fri" to 7f,
            "Sat" to 6f,
            "Sun" to 0f,
            )

        val animationDuration = 1000

        val linechart = view.findViewById<LineChartView>(R.id.lineChart)

        linechart.apply {
            gradientFillColors = intArrayOf(
                Color.parseColor("#989898"),
                Color.TRANSPARENT
            )
            animation.duration = animationDuration.toLong()
            animate(dataSet)
        }
    }
}