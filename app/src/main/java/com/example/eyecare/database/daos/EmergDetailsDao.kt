package com.example.eyecare.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.eyecare.database.entities.EmergencyDetails

@Dao
interface EmergDetailsDao {
    @Query("SELECT * FROM EmergencyDetails")
    fun getAll(): List<EmergencyDetails>

    @Insert
    fun insert(user: EmergencyDetails)

    @Update
    fun update(user: EmergencyDetails)

    @Delete
    fun delete(user: EmergencyDetails)
}