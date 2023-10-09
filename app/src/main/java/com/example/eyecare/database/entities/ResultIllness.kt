package com.example.eyecare.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eyecare.database.Illness

@Entity(tableName = "result")
data class ResultIllness (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "cause")
    val cause: List<String>,
    @ColumnInfo(name = "description")
    val description: List<String>
){
    constructor(illness: Illness) : this(
        name = illness.name,
        cause=illness.cause,
        description = illness.des

        // Initialize other fields as needed
    )
}