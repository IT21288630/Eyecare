package com.example.eyecare.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checked_items")
data class checked_symp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
