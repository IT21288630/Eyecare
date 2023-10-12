package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.eyecare.*
import com.example.eyecare.database.Illness
import com.example.eyecare.database.IllnessChecker
import com.example.eyecare.database.SympDatabase
import com.example.eyecare.database.entities.ResultIllness
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EyeTest_Result : AppCompatActivity() {

    private lateinit var illness: TextView
    private lateinit var cause: TextView
    private lateinit var des: TextView
    private lateinit var retry: Button
    private lateinit var results: ImageButton
    private lateinit var illnessChecker: IllnessChecker
    private lateinit var db: SympDatabase

    //private lateinit var symptomDao: checked_sympDao


    //private lateinit var symptoms: LiveData<List<String>>
    //private lateinit var symptomViewModel: SymptomViewModel
    //private lateinit var repository: SymptomRepository
    private var scope = CoroutineScope(Dispatchers.IO)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)





        db = Room.databaseBuilder(
            applicationContext,
            SympDatabase::class.java, "symptoms-database"
        ).build()








        illnessChecker = IllnessChecker(this)
//        val checkedSymptoms = db.symptomDao().getCheckedSymptoms()

        //symptomDao = checkboxDatabase.getDatabase(this).checked_sympDao()
        //symptoms = symptomDao.getSymptoms()
        /*scope.launch {
            getResult()
        }*/


        //repository =SymptomRepository(symptomDao)
        //symptomViewModel = ViewModelProvider(this, SymptomViewModelFactory(repository))[SymptomViewModel::class.java]


        illness = findViewById(R.id.results)
        cause = findViewById(R.id.causes)
        des = findViewById(R.id.description)
        retry = findViewById(R.id.retrytest)
        results = findViewById(R.id.resultList)



        illness.text = ""
        cause.text = ""

        loadSymptomsAndCalculateIllness()

        retry.setOnClickListener {
            val intent = Intent(this, EyeTrackerMainActivity::class.java)
            startActivity(intent)
        }

        results.setOnClickListener {
            val intent = Intent(this, TestResultList::class.java)
            startActivity(intent)
        }


    }

    private fun loadSymptomsAndCalculateIllness() {

        val owner: LifecycleOwner = this
        scope.launch {
            val storedSymptomsLiveData = db.symptomDao().getCheckedSymptoms()


            withContext(Dispatchers.Main) {
                storedSymptomsLiveData.observe(owner, Observer { symptomsList ->
                    //  access  the list of symptoms
                    if (symptomsList != null) {
                        val mostSuitableIllness =
                            illnessChecker.findMostSuitableIllness(symptomsList)

                        if (mostSuitableIllness != null) {

                            val suitableIllness = ResultIllness(mostSuitableIllness)
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    db.ResultillnessDao().insert(suitableIllness)

                                }
                            }



                            runOnUiThread {
                                updateUIWithIllnessResult(mostSuitableIllness)
                            }

                        }


                    }

                })
            }


        }
    }


    private fun updateUIWithIllnessResult(mostSuitableIllness: Illness?) {
        if (mostSuitableIllness != null) {
            illness.text = "Most Suitable Illness: ${mostSuitableIllness.name}"
            val causesText = mostSuitableIllness.cause.joinToString("\n") { "• $it" }
            cause.text = HtmlCompat.fromHtml(causesText, HtmlCompat.FROM_HTML_MODE_LEGACY)

            val desText = mostSuitableIllness.des.joinToString("\n") { "• $it" }
            des.text = HtmlCompat.fromHtml(desText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            illness.text = "No suitable illness found."
            cause.text = "" // Clear the cause text if no illness is found
            des.text = ""
        }
    }

}





   /* }



    @SuppressLint("SetTextI18n")
    private fun getResult() {

        val storedSymptomsLiveData =db.symptomDao().getCheckedSymptoms()
        
        val mostSuitableIllness = illnessChecker.findMostSuitableIllness(storedSymptomsLiveData)

        if (mostSuitableIllness != null) {
            illness.text = "Most Suitable Illness: ${mostSuitableIllness.name}"
            val causesText = mostSuitableIllness.cause.joinToString("\n") { "&#8226; $it" }
            cause.text = HtmlCompat.fromHtml(causesText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            illness.text = "No suitable illness found."
        }

    }
}*/