package com.example.eyecare.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eyecare.database.daos.SymptomDao
import com.example.eyecare.database.entities.SymptomEntity

@Database(entities = [SymptomEntity::class], version = 1)

abstract class SympDatabase : RoomDatabase() {
    abstract fun symptomDao(): SymptomDao
}