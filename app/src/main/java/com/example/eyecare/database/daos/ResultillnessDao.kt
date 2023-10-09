package com.example.eyecare.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eyecare.database.Illness
import com.example.eyecare.database.entities.ResultIllness

@Dao
interface ResultillnessDao {
    @Insert
    fun insert(ResultIllness: ResultIllness?)
    @Query("SELECT * FROM result")
    fun getAllSymptoms(): LiveData<List<ResultIllness>>

    @Delete
    fun delete(ResultIllness: ResultIllness?)

}