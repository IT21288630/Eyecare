package com.example.eyecare.activities
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.EmergencyDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivateEmergencyContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_emergency_contact)

        var activatedDate = findViewById<TextView>(R.id.activatedDateTxt)
        var activateTime = findViewById<TextView>(R.id.activatedTimeTxt)
        var activatedLocation = findViewById<TextView>(R.id.activatedLocationTxt)

        activatedDate.text = getCurrentDate()
        activateTime.text = getCurrentTimeDigital()



        GlobalScope.launch(Dispatchers.IO) {
            fetchDetails()
        }


    }

    suspend fun fetchDetails(){

        var currentContact1 = findViewById<TextView>(R.id.activatedNumber1)
        var currentContact2 = findViewById<TextView>(R.id.activatedNumber2)
        var currentContact3 = findViewById<TextView>(R.id.activatedNumber3)
        var currentName = findViewById<TextView>(R.id.activatedNameTxt)


        coroutineScope {
            val dataList: List<EmergencyDetails> = getEmergencyDetails()
            launch(Dispatchers.Main) {
                // Update UI on the main thread
                currentName.text =dataList.getOrNull(0)?.uName ?: "Name not found"
                currentContact1.text = (dataList.getOrNull(0)?.contNo1 ?: "Not available" )
                currentContact2.text = (dataList.getOrNull(0)?.contNo2 ?: "Not available")
                currentContact3.text = (dataList.getOrNull(0)?.contNo3 ?: "Not available")

                createPdf()

            }
        }
    }

    //Retrieving data from database
    suspend fun getEmergencyDetails(): List<EmergencyDetails> {

        val db = EyecareDatabase.getInstance(this@ActivateEmergencyContactActivity)
        val emrgDao = db.getEmergencyDetails()

        return emrgDao.getAll()

    }

    fun getCurrentDate(): String {

        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        // Define a date format
        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getCurrentTimeDigital(): String {

        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)

        // Define a time format
        val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        return timeFormat.format(date)
    }

    private fun createPdf() {
        // Create a new PdfDocument
        val pdfDocument = PdfDocument()

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // Create a PageInfo
        val pageInfo = PdfDocument.PageInfo.Builder(screenWidth, screenHeight, 1).create()

        // Start a new page
        val page = pdfDocument.startPage(pageInfo)

        // Get the root view of your layout
        val rootView = findViewById<View>(android.R.id.content)

        // Create a bitmap of the root view
        val bitmap = createBitmapFromView(rootView)

        // Draw the bitmap onto the PDF page
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // Finish the page
        pdfDocument.finishPage(page)

        // Save the PDF to the device's external storage (Downloads folder)
        try {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val pdfFile = File(downloadDir, "EyeCare emergency notice.pdf")
            val outputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(outputStream)
            // Notify the user
            showToast("PDF created successfully!")
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        // Close the PdfDocument
        pdfDocument.close()
    }

    private fun createBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun showToast(message: String) {
        runOnUiThread { Toast.makeText(this@ActivateEmergencyContactActivity, message, Toast.LENGTH_SHORT).show() }
    }

}