package com.example.eyecare.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(
    val name: String,
    val dose: String,
    val time: Long,
    val medId: Int,
    val date: String,
    val isTaken: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Int? = null
}
