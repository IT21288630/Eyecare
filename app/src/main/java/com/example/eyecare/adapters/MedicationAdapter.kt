package com.example.eyecare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.database.entities.Medication

class MedicationAdapter() : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    lateinit var data: List<Medication>
    lateinit var context: Context

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
        }
    }

    fun setData(data: List<Medication>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
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
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}