package com.example.eyecare.database.daos

import androidx.room.*
import com.example.eyecare.database.entities.SymptomEntity

@Dao
interface checked_sympDao {

    @Insert
    fun insertCheckboxItem(checkboxItem: SymptomEntity)

    /*//@Update
    //suspend fun updateCheckboxItem(checkboxItem: checked_symp)

   // @Delete
    //suspend fun deleteCheckboxItem(checkboxItem: checked_symp)*/

    //@Query("SELECT * FROM checked_items")
   // fun getAllCheckboxItems(): MutableList<List<checked_symp>>

    @Query("SELECT name FROM checked_items")
    fun getSymptoms(): List<String>

    @Query("SELECT COUNT(name) FROM checked_items")
    fun checkedItems():Int
}