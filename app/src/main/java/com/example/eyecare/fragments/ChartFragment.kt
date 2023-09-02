package com.example.eyecare.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.db.williamchart.view.BarChartView
import com.example.eyecare.R

class ChartFragment : Fragment(R.layout.fragment_chart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barset = listOf(
            "Panadol" to 10f, "Paracetemol" to 5f, "Eye drop" to 8f
        )

        val animationDuration = 1000

        val barchart = view.findViewById<BarChartView>(R.id.barChart)

        barchart.animation.duration = animationDuration.toLong()
        barchart.animate(barset)
    }
}