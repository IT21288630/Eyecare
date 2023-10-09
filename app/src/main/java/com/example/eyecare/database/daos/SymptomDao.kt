package com.example.eyecare.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eyecare.database.entities.SymptomEntity
//import com.example.eyecare.database.entities.checked_symp

@Dao
interface SymptomDao {



    @Query("SELECT * FROM symptoms")
    fun getAllSymptoms(): List<SymptomEntity>

    @Query("SELECT name FROM symptoms WHERE isChecked = 1")
    fun getCheckedSymptoms(): LiveData<List<String>>

   
    // Other DAO methods for CRUD operations
}