package com.example.eyecare.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.UpdateMedicationActivity
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.Medication
import com.example.eyecare.database.repositories.MedicationRepository
import com.example.eyecare.database.repositories.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MedicationAdapter(
    private var data: List<Medication>,
    private var context: Context
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDose: TextView
        val tvOpt: TextView
        val tvTime: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDose = itemView.findViewById(R.id.tvDose)
            tvOpt = itemView.findViewById(R.id.tvOpt)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    suspend fun setData(data: List<Medication>, context: Context) {
        this.data = data
        this.context = context
        withContext(Dispatchers.Main) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicationAdapter.MedicationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medication, parent, false)
        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationAdapter.MedicationViewHolder, position: Int) {
        holder.apply {
            tvName.text = data[position].name
            tvDose.text = data[position].dose

            val dt = Date(data[position].time)
            val sdf = SimpleDateFormat("hh:mm aa")
            val time: String = sdf.format(dt)

            tvTime.text = time

            val medicationRepository = MedicationRepository(EyecareDatabase.getInstance(context))
            val scheduleRepository = ScheduleRepository(EyecareDatabase.getInstance(context))

            tvOpt.setOnClickListener {
                val popupMenu = PopupMenu(context, tvOpt)
                popupMenu.apply {
                    inflate(R.menu.med_option_menu)
                    show()
                }

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.deleteMed -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                medicationRepository.deleteMedication(data[position])
                                data[position].id?.let { id ->
                                    scheduleRepository.deleteScheduleItem(
                                        id
                                    )
                                }

                                val data = medicationRepository.getAllMedications()
                                setData(data, context)
                            }
                        }
                        R.id.updateMed -> {
                            val intent = Intent(context, UpdateMedicationActivity::class.java)
                            intent.putExtra("name", data[position].name)
                            intent.putExtra("dose", data[position].dose)
                            intent.putExtra("time", data[position].time)
                            intent.putExtra("id", data[position].id)
                            ContextCompat.startActivity(context, intent, null)
                        }
                    }
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}