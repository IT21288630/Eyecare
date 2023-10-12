package com.example.eyecare.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.db.williamchart.view.LineChartView
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.entities.Schedule
import com.example.eyecare.database.repositories.MedicationRepository
import com.example.eyecare.database.repositories.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class ChartFragment : Fragment(R.layout.fragment_chart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val medicationList = view.findViewById<AutoCompleteTextView>(R.id.medicationList)
        val lineChart = view.findViewById<LineChartView>(R.id.lineChart)
        val progressBarOnTime = view.findViewById<ProgressBar>(R.id.progressBarOnTime)
        val progressBarLate = view.findViewById<ProgressBar>(R.id.progressBarLate)
        val progressBarMissed = view.findViewById<ProgressBar>(R.id.progressBarMissed)
        val tvProgressOnTime = view.findViewById<TextView>(R.id.tvProgressOnTime)
        val tvProgressLate = view.findViewById<TextView>(R.id.tvProgressLate)
        val tvProgressMissed = view.findViewById<TextView>(R.id.tvProgressMissed)
        val tvOnTime = view.findViewById<TextView>(R.id.tvOnTime)
        val tvLate = view.findViewById<TextView>(R.id.tvLate)
        val tvMissed = view.findViewById<TextView>(R.id.tvMissed)

        val medicationRepository = MedicationRepository(EyecareDatabase.getInstance(view.context))
        val scheduleRepository = ScheduleRepository(EyecareDatabase.getInstance(view.context))
        var allSchedules: List<Schedule>
        var onTime = 0
        var late = 0
        var missed = 0
        var medications: List<Medication>
        var schedules: List<Schedule>
        var weeklySummery = mutableListOf(
            "Mon" to 0f,
            "Tue" to 0f,
            "Wed" to 0f,
            "Thu" to 0f,
            "Fri" to 0f,
            "Sat" to 0f,
            "Sun" to 0f,
        )
        var names = mutableListOf<String>()

        val animationDuration = 1000

        lineChart.apply {
            gradientFillColors = intArrayOf(
                Color.parseColor("#989898"), Color.TRANSPARENT
            )
            animation.duration = animationDuration.toLong()
            animate(weeklySummery)
        }

        CoroutineScope(Dispatchers.IO).launch {
            medications = medicationRepository.getAllMedications()
            allSchedules = scheduleRepository.getScheduleAll(LocalDate.now().toString() + "%")
            schedules = scheduleRepository.getScheduleForChart()

            for (medication in medications) {
                names.add(medication.name)
            }

            for (item in allSchedules) {
                var timeToCheck = item.time
                var markedTime = LocalDateTime.parse(item.date)

                val calendar = Calendar.getInstance()
                calendar.set(
                    Calendar.YEAR, Calendar.MONTH, Calendar.DATE, markedTime.hour, markedTime.minute
                )

                var markedTimeLong = calendar.timeInMillis

                // Extracting minutes from markedTimeLong
                val markedTimeCalendar = Calendar.getInstance()
                markedTimeCalendar.timeInMillis = markedTimeLong
                val markedTimeMinutes = markedTimeCalendar.get(Calendar.MINUTE)

                // Extracting minutes from timeToCheck
                val timeToCheckCalendar = Calendar.getInstance()
                timeToCheckCalendar.timeInMillis = timeToCheck
                val timeToCheckMinutes = timeToCheckCalendar.get(Calendar.MINUTE)

                if (markedTimeMinutes == timeToCheckMinutes && item.isTaken) {
                    onTime++
                } else if (markedTimeMinutes > timeToCheckMinutes && item.isTaken) {
                    late++
                } else {
                    missed++
                }
            }

            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    view.context, R.layout.dropdown_item, names
                )
                medicationList.setAdapter(adapter)

                tvOnTime.text = "$onTime Taken on Time"
                var percentage = onTime.toDouble() / (onTime + late + missed) * 100
                tvProgressOnTime.text = "${percentage.toInt()}%"
                progressBarOnTime.progress = percentage.toInt()

                tvLate.text = "$late Late Doses"
                percentage = late.toDouble() / (onTime + late + missed) * 100
                tvProgressLate.text = "${percentage.toInt()}%"
                progressBarLate.progress = percentage.toInt()

                tvMissed.text = "$missed Missed Doses"
                percentage = missed.toDouble() / (onTime + late + missed) * 100
                tvProgressMissed.text = "${percentage.toInt()}%"
                progressBarMissed.progress = percentage.toInt()

                medicationList.doOnTextChanged { text, _, _, _ ->
                    var count = 0F

                    for (schedule in schedules) {
                        if (schedule.name.contains(text!!, true) && schedule.isTaken) {
                            val sdf = SimpleDateFormat("yyyy-MM-dd")
                            val sdf2 = SimpleDateFormat("EEE")
                            val d = sdf.parse(schedule.date)
                            val dayOfTheWeek: String = sdf2.format(d)

                            weeklySummery[weeklySummery.indexOfFirst {
                                it.first == dayOfTheWeek
                            }] = dayOfTheWeek to ++count

                            count = 0F
                        }
                    }

                    lineChart.apply {
                        gradientFillColors = intArrayOf(
                            Color.parseColor("#989898"), Color.TRANSPARENT
                        )
                        animation.duration = animationDuration.toLong()
                        animate(weeklySummery)
                    }
                }
            }

        }

        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }
    }
}