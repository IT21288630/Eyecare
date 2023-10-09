package com.example.eyecare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.database.entities.ResultIllness

class ResultAdapter(private val onDeleteClickListener: (ResultIllness) -> Unit): RecyclerView.Adapter<ResultAdapter.SymptomViewHolder>() {
    private var symptoms: List<ResultIllness> = emptyList()

    inner class SymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val illnessNameTextView: TextView = itemView.findViewById(R.id.illness)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteResult)

        fun bind(symptom: ResultIllness) {

            illnessNameTextView.text = symptom.name

            deleteButton.setOnClickListener {
                onDeleteClickListener(symptom)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.`item_result`, parent, false)
        return SymptomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        holder.bind(symptoms[position])
    }

    override fun getItemCount(): Int {
        return symptoms.size
    }

    fun setSymptoms(symptoms: List<ResultIllness>) {
        this.symptoms = symptoms
        notifyDataSetChanged()
    }
}
