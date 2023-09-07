package com.example.eyecare.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmergencyDetails(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "uName") var uName: String?,
    @ColumnInfo(name = "Contact_no_1") var contNo1: String?,
    @ColumnInfo(name = "Contact_no_2") var contNo2: String?,
    @ColumnInfo(name = "Contact_no_3") var contNo3: String?,
    @ColumnInfo(name = "Emergency_msg") var emergMsg: String?,
)
