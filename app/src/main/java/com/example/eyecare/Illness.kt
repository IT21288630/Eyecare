package com.example.eyecare

import android.content.Context
import androidx.lifecycle.LiveData
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

    init {
        val json = context.assets.open("illness.json").bufferedReader().use { it.readText() }
        illnesses = gson.fromJson(json, object : TypeToken<List<Illness>>() {}.type)
    }

    fun findMostSuitableIllness(symptoms: List<String>): Illness? {

        var bestMatch: Illness? = null
        var bestScore = Int.MAX_VALUE

        for (illness in illnesses) {
            val score = calculateScore(symptoms, illness.symptoms)
            if (score < bestScore) {
                bestScore = score
                bestMatch = illness
            }
        }

        return bestMatch
    }

    private fun calculateScore(inputSymptoms: List<String>, illnessSymptoms: List<String>): Int {
        var score = 0

        for (symptom in inputSymptoms) if (!illnessSymptoms.contains(symptom)) {
            score++
        }

        return score
    }
}





