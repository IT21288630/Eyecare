package com.example.eyecare.database

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Illness (
    val name: String,
    val symptoms: List<String>,
    val cause: List<String>,
    val des: List<String>
)

class IllnessChecker(context: Context ) {
    private val gson = Gson()
    private val illnesses: List<Illness>
    private lateinit var db: SympDatabase



    init {

        val json =
            context.assets.open("illness.json").bufferedReader().use { it.readText() }
        illnesses = gson.fromJson(json, object : TypeToken<List<Illness>>() {}.type)

    }

    fun findMostSuitableIllness(illSymptoms: List<String>): Illness? {





        var bestMatch: Illness? = null
        var bestScore = Int.MAX_VALUE

        for (illness in illnesses) {
            val score = calculateScore(illSymptoms, illness.symptoms)
            if (score < bestScore) {
                bestScore = score
                bestMatch = illness
            }
        }

        return bestMatch
    }

    private fun calculateScore(inputSymptoms: List<String>, illnessSymptoms: List<String>): Int {
        var score = 0


        for (symptom in inputSymptoms)// if (!illnessSymptoms.contains(symptom))
        {
            for (symptoms in illnessSymptoms) {
                if (symptom == symptoms) {
                    score++

                }
            }
        }

            return score

    }}






