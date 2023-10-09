package com.example.eyecare.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eyecare.database.daos.ResultillnessDao
import com.example.eyecare.database.daos.SymptomDao
import com.example.eyecare.database.entities.ResultIllness
import com.example.eyecare.database.entities.SymptomEntity

@Database(entities = [SymptomEntity::class,ResultIllness::class], version = 1)
@TypeConverters(StringListConverter::class) // Add your TypeConverter here

abstract class SympDatabase : RoomDatabase() {
    abstract fun symptomDao(): SymptomDao

    abstract fun ResultillnessDao(): ResultillnessDao
}
