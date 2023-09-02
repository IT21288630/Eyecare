package com.example.eyecare.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medication(
    val name: String,
    val dose: String,
    val time: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
