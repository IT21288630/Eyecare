package com.example.eyecare.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.adapters.MedicationAdapter
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.repositories.MedicationRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewMedicationFragment : Fragment(R.layout.fragment_view_medication) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicationRepository = MedicationRepository(EyecareDatabase.getInstance(view.context))
        val ui = view.context
        val medicationAdapter = MedicationAdapter(listOf(), ui)
        val rvMedications = view.findViewById<RecyclerView>(R.id.rvMedications)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        val deleteAllMedBtn = view.findViewById<Button>(R.id.deleteAllMedBtn)

        rvMedications.adapter = medicationAdapter
        rvMedications.layoutManager = LinearLayoutManager(view.context)

        backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = medicationRepository.getAllMedications()
            medicationAdapter.setData(data, ui)
        }

        deleteAllMedBtn.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(view.context)
            materialAlertDialogBuilder.apply {
                setTitle("Confirm")
                setMessage("Are you sure that you want to delete all the medication?")
                setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        medicationRepository.deleteAllMedication()
                        val data = medicationRepository.getAllMedications()

                        withContext(Dispatchers.Main) {
                            medicationAdapter.setData(data, ui)
                        }
                    }

                    dialog.dismiss()
                })
                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
                create()
                show()
            }
        }
    }
}