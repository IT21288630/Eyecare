package com.example.eyecare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.R
import com.example.eyecare.activities.DisplaySymptomActivity
import com.example.eyecare.database.checkboxDatabase
import com.example.eyecare.database.daos.checked_sympDao
import com.example.eyecare.database.entities.SymptomEntity
import com.example.eyecare.database.entities.checked_symp

//import com.example.eyecare.database.entities.checked_symp

class SymptomAdapter(
    private val context: Context,
    private val symptoms: List<SymptomEntity>,
    val database: checkboxDatabase,

    private var items: List<checked_symp> = emptyList(),
    private var selectedSymptoms: MutableList<checked_symp> = mutableListOf()




) :
    RecyclerView.Adapter<SymptomAdapter.ViewHolder>() {



    //var checked1 = mutableListOf<checked>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_symptom, parent, false)




        return ViewHolder(view)
    }



      override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          val symptom = symptoms[position]
          //val symp = items[position]


          holder.checkBox.text = symptom.name
          holder.checkBox.isChecked = symptom.isChecked
          //holder.checkBox.isChecked = selectedSymptoms.contains(symp)


          holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
              symptom.isChecked = isChecked

              /*if (isChecked) {
              } else {
                 // selectedSymptoms.remove(symptom)
              }*/






          }

      }

      override fun getItemCount(): Int {
          return symptoms.size
      }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

    }
    /*fun returnChecked():List<checked>{
        return checked1
    }*/
}








