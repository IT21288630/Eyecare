package com.example.eyecare.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.eyecare.R
import com.example.eyecare.adapters.ScheduleAdapter
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.repositories.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class TodayScheduleFragment : Fragment(R.layout.fragment_today_schedule) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scheduleRepository = ScheduleRepository(EyecareDatabase.getInstance(view.context))
        val ui = view.context
        val scheduleAdapter = ScheduleAdapter(listOf(), ui)
        val rvSchedule = view.findViewById<RecyclerView>(R.id.rvSchedule)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        rvSchedule.adapter = scheduleAdapter
        rvSchedule.layoutManager = LinearLayoutManager(view.context)

        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = scheduleRepository.getSchedule(LocalDate.now().toString() + "%")
            scheduleAdapter.setData(data, ui)
        }

        swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                val data = scheduleRepository.getSchedule(LocalDate.now().toString() + "%")
                scheduleAdapter.setData(data, ui)
            }

            swipeRefreshLayout.isRefreshing = false
        }
    }
}