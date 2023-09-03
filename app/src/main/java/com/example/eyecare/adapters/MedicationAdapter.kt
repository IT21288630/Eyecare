package com.example.eyecare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.database.entities.Medication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicationAdapter(
    private var data: List<Medication>,
    private var context: Context
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDose: TextView
        val tvOpt: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDose = itemView.findViewById(R.id.tvDose)
            tvOpt = itemView.findViewById(R.id.tvOpt)
        }
    }

    suspend fun setData(data: List<Medication>, context: Context) {
        this.data = data
        this.context = context
        withContext(Dispatchers.Main){
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
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}