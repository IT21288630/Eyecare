package com.example.eyecare.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity
data class Schedule(
    @Embedded val medication: Medication,
    val date: String,
    val isTaken: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Int? = null
}
