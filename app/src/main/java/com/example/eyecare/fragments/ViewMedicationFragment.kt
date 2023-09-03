package com.example.eyecare.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.adapters.MedicationAdapter
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.repositories.MedicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewMedicationFragment : Fragment(R.layout.fragment_view_medication) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = MedicationRepository(EyecareDatabase.getInstance(view.context))
        val ui = view.context
        val medicationAdapter = MedicationAdapter(listOf(), ui)
        val rvMedications = view.findViewById<RecyclerView>(R.id.rvMedications)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }

        rvMedications.adapter = medicationAdapter
        rvMedications.layoutManager = LinearLayoutManager(view.context)

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllMedications()
            medicationAdapter.setData(data, ui)
        }
    }
}