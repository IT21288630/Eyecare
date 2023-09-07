package com.example.eyecare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
import java.time.temporal.ChronoUnit
import java.util.*

class ScheduleAdapter(
    private var data: List<Schedule>,
    private var context: Context
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {


    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDose: TextView
        val tvTime: TextView
        val markBtn: Button

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDose = itemView.findViewById(R.id.tvDose)
            tvTime = itemView.findViewById(R.id.tvTime)
            markBtn = itemView.findViewById(R.id.markBtn)
        }
    }

    suspend fun setData(data: List<Schedule>, context: Context) {
        this.data = data
        this.context = context
        withContext(Dispatchers.Main) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.apply {
            tvName.text = data[position].name
            tvDose.text = data[position].dose

            val dt = Date(data[position].time)
            val sdf = SimpleDateFormat("hh:mm aa")
            val time: String = sdf.format(dt)

            tvTime.text = time

            val scheduleRepository = ScheduleRepository(EyecareDatabase.getInstance(context))

            markBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    data[position].scheduleId?.let { scheduleId ->
                        scheduleRepository.markAsTaken(
                            scheduleId
                        )
                    }

                    val medication = Medication(
                        data[position].name,
                        data[position].dose,
                        data[position].time
                    )

                    scheduleRepository.insertToScheduleAfter(
                        Schedule(
                            medication.name,
                            medication.dose,
                            medication.time,
                            data[position].medId,
                            LocalDate.now().plus(1, ChronoUnit.DAYS).toString()
                        )
                    )

                    val data = scheduleRepository.getSchedule(LocalDate.now().toString())
                    setData(data, context)
                }

                Toast.makeText(context, "Marked as taken", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}