package com.example.eyecare.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmergencyDetails(
    @PrimaryKey val uName: String,
    @ColumnInfo(name = "Contact_no_1") val contNo1: String?,
    @ColumnInfo(name = "Contact_no_2") val contNo2: String?,
    @ColumnInfo(name = "Contact_no_3") val contNo3: String?,
    @ColumnInfo(name = "Emergency_msg") val emergMsg: String?,
)
