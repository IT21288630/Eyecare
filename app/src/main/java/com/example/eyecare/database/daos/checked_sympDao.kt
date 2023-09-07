package com.example.eyecare.database.daos

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eyecare.database.entities.checked_symp

@Dao
interface checked_sympDao {

    @Insert
    fun insertCheckboxItem(checkboxItem: checked_symp)

    /*//@Update
    //suspend fun updateCheckboxItem(checkboxItem: checked_symp)

   // @Delete
    //suspend fun deleteCheckboxItem(checkboxItem: checked_symp)*/

    //@Query("SELECT * FROM checked_items")
   // fun getAllCheckboxItems(): MutableList<List<checked_symp>>

    @Query("SELECT name FROM checked_items")
    fun getSymptoms(): List<String>
}