package com.example.eyecare.activities

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.eyecare.*
import com.example.eyecare.Notification
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class AddMedicationActivity : AppCompatActivity() {

    private val RQ_SPEECH_REC = 102
    private val RQ_SPEECH_REC2 = 103
    private lateinit var etMedication: EditText
    private lateinit var etDose: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medication)

        val backBtn = findViewById<CardView>(R.id.backBtn)
        etMedication= findViewById(R.id.etMedication)
        etDose = findViewById(R.id.etDose)
        val setBtn: Button = findViewById(R.id.setBtn)
        val timeP: TimePicker = findViewById(R.id.timeP)
        val addMedBtn: ImageButton = findViewById(R.id.addMedBtn)
        val addDoseBtn: ImageButton = findViewById(R.id.addDoseBtn)

        backBtn.setOnClickListener {
            finish()
        }

        addMedBtn.setOnClickListener {
            speechInput(RQ_SPEECH_REC, "Insert Medication")
        }

        addDoseBtn.setOnClickListener {
            speechInput(RQ_SPEECH_REC2, "Insert Dosage")
        }

        createNotificationChannel()

        setBtn.setOnClickListener {
            scheduleNotification(etMedication.text.toString(), etDose.text.toString(), timeP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            etMedication.setText(result?.get(0).toString())
        }

        if (requestCode == RQ_SPEECH_REC2 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            etDose.setText(result?.get(0).toString())
        }
    }

    private fun speechInput(RQ_SPEECH_REC: Int, message: String) {
        if (!SpeechRecognizer.isRecognitionAvailable(this@AddMedicationActivity)) {
            Toast.makeText(this@AddMedicationActivity, "Speech recognizer not available!", Toast.LENGTH_LONG)
                .show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, message)
            startActivityForResult(intent, RQ_SPEECH_REC)
        }
    }

    private fun scheduleNotification(
        etMedication: String,
        etMsg: String,
        timeP: TimePicker
    ) {
        val intent = Intent(applicationContext, Notification::class.java)

        intent.putExtra(titleExtra, etMedication)
        intent.putExtra(messageExtra, etMsg)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(timeP)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        Toast.makeText(
            applicationContext,
            "Alarm is a go at " + dateFormat.format(date) + " " + timeFormat.format(date),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getTime(timeP: TimePicker): Long {
        val minute = timeP.minute
        val hour = timeP.hour

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, hour, minute)

        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Reminder"
        val description = "Take your medication now!"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}