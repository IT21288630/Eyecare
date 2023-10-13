package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.eyecare.*
import com.example.eyecare.database.Illness
import com.example.eyecare.database.IllnessChecker
import com.example.eyecare.database.SympDatabase
import com.example.eyecare.database.checkboxDatabase
import com.example.eyecare.database.entities.ResultIllness
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class EyeTest_Result : AppCompatActivity() {

    private lateinit var illness: TextView
    private lateinit var cause: TextView
    private lateinit var des: TextView
    private lateinit var retry: Button
    private lateinit var downloadReportButton: Button
    private lateinit var illnessChecker: IllnessChecker
    private lateinit var db: checkboxDatabase
    private lateinit var database: SympDatabase
    private lateinit var resultIllness: Illness
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
            checkboxDatabase::class.java, "checkedDatabase"
        ).build()

        database = Room.databaseBuilder(
            applicationContext,
            SympDatabase::class.java, "Symptom-Database"
        ).build()

        downloadReportButton = findViewById<Button>(R.id.downloadReport)
        downloadReportButton.setOnClickListener {
            generateAndExportReport()
        }





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
        val results = findViewById<ImageView>(R.id.resultList)



        illness.text = ""
        cause.text = ""

        loadSymptomsAndCalculateIllness()

        retry.setOnClickListener {
            val intent = Intent(this, EyeTrackerMainActivity::class.java)
            startActivity(intent)
        }

        results.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadSymptomsAndCalculateIllness() {

        val owner: LifecycleOwner = this
        scope.launch {
            val storedSymptomsLiveData = database.symptomDao().getCheckedSymptoms()


            withContext(Dispatchers.Main) {
                storedSymptomsLiveData.observe(owner, Observer { symptomsList ->
                    //  access  the list of symptoms
                    if (symptomsList != null) {
                        val mostSuitableIllness =
                            illnessChecker.findMostSuitableIllness(symptomsList)

                        if (mostSuitableIllness != null) {

                            resultIllness=mostSuitableIllness
                            val suitableIllness = ResultIllness(mostSuitableIllness)
                            scope.launch {
                                withContext(Dispatchers.IO){
                                    database.ResultillnessDao().insert(suitableIllness)

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

    private fun generateAndExportReport() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 page size (595x842 points)
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        val paint = Paint()
        val text = generateReportContent(resultIllness)

        val pageWidth = pageInfo.pageWidth
        val pageHeight = pageInfo.pageHeight



        // Define initial position and text size
        var xPosition = 50f
        var yPosition = 50f
        var textSize = 18f // Adjust as needed for larger text
        val lineHeight = textSize * 1.2f // Adjust line spacing as needed


        // Split the text into lines
        val lines = text.split("\n")

        for ((index, line) in lines.withIndex()) {
            val bounds = Rect()
            paint.textSize = textSize
            paint.getTextBounds(line, 0, line.length, bounds)

            if (index < 2) {
                // Center and enlarge the first two lines
                paint.textSize = 24f // Adjust for larger text size
                paint.getTextBounds(line, 0, line.length, bounds)
                xPosition = (pageWidth - bounds.width()) / 2f
            } else {
                // Reset text size and position
                paint.textSize = textSize
                xPosition = 50f
            }

            // Check if the text goes beyond the page height
            if (yPosition + lineHeight > pageHeight - 50) {
                pdfDocument.finishPage(page)
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                xPosition = 50f
                yPosition = 50f
            }

            // Draw the text on the page
            canvas.drawText(line, xPosition, yPosition, paint)
            yPosition += lineHeight
        }
        // Finish the page
        pdfDocument.finishPage(page)

        // Create a directory to store the PDF file
        val directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        directory?.mkdirs()

        // Generate a unique file name for the PDF report
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "EYECARE_Report_$timeStamp.pdf"

        // Create a File object for the report file
        val reportFile = File(directory, fileName)

        try {
            // Create an output stream for the PDF file
            val outputStream = FileOutputStream(reportFile)

            // Write the PDF document to the output stream
            pdfDocument.writeTo(outputStream)

            // Close the document and output stream
            pdfDocument.close()
            outputStream.close()

            // Notify the MediaScanner to refresh the file so it's visible in file explorers
            MediaScannerConnection.scanFile(
                this,
                arrayOf(reportFile.path),
                null,
                null
            )

            // Open the downloaded PDF report using an intent
            val openIntent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(
                this,
                "com.example.eyecare.fileprovider",
                reportFile
            )
            openIntent.setDataAndType(uri, "application/pdf")
            openIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(openIntent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun generateReportContent(mostSuitableIllness: Illness?): String {
        return if (mostSuitableIllness != null) {
            val builder = StringBuilder()
            builder.append("Eye Health Tracker\n\n")
            builder.append("Test Results\n\n\n")
            builder.append("Most Suitable Illness: ${mostSuitableIllness.name}\n\n")
            builder.append("Causes:\n")
            mostSuitableIllness.cause.forEachIndexed { index, cause ->
                builder.append("${index + 1}. $cause\n")
            }
            builder.append("\nDescription:\n")
            mostSuitableIllness.des.forEachIndexed { index, description ->
                builder.append("${index + 1}. $description\n")
            }
            builder.toString()
        } else {
            "No suitable illness found."
        }
    }

    private fun exportReport(content: String): String? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "Report_$timeStamp.txt"

        val directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val reportFile = File(directory, fileName).absolutePath

        try {
            FileWriter(reportFile).use { writer ->
                writer.write(content)
            }
            return reportFile
        } catch (e: IOException) {
            e.printStackTrace()
            return null
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