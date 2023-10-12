package com.example.eyecare.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class SymptomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    var isChecked: Boolean
)
