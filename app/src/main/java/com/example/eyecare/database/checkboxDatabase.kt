package com.example.eyecare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eyecare.database.daos.checked_sympDao
import com.example.eyecare.database.entities.checked_symp

@Database(entities = [checked_symp::class], version = 1)
abstract class checkboxDatabase : RoomDatabase() {
    abstract fun checked_sympDao():checked_sympDao

    companion object {
        private var INSTANCE: checkboxDatabase? = null

        fun getDatabase(context: Context): checkboxDatabase {
            if (INSTANCE == null) {
                synchronized(checkboxDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        checkboxDatabase::class.java,
                        "checkedDatabase"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}