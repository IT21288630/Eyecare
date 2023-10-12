package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.eyecare.R


class ColorIdentifierActivity : AppCompatActivity() {

     lateinit var selected_image : ImageView
     var REQUEST_IMAGE_CAPTURE : Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coloridentifier)

        selected_image = findViewById(R.id.selected_image)
        var color_display: ImageView? = null
        var rgbcolor: String? = null
        var hexcolor: String? = null
        var hexValue: TextView? = null
        var rgbValue: TextView? = null
        var colordis : TextView? = null

         REQUEST_IMAGE_CAPTURE = 2 // Use a different request code than the one used for selecting images

        val capturePictureButton = findViewById<Button>(R.id.capturePictureButton)

        capturePictureButton.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this@ColorIdentifierActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@ColorIdentifierActivity, arrayOf(android.Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

        val choose_Image = findViewById<Button>(R.id.Choose_image)
        hexValue = findViewById(R.id.hexValue)
        rgbValue = findViewById(R.id.rgbValue)
        color_display = findViewById(R.id.color_display)

        colordis = findViewById(R.id.colordis) // Initialize colordis TextView
        choose_Image.setOnClickListener(View.OnClickListener {
            val pickImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImage, 1)
        })
        selected_image.setOnTouchListener(OnTouchListener { view, motionEvent ->
            try {
                val action = motionEvent.action
                val evX = motionEvent.x.toInt()
                val evY = motionEvent.y.toInt()
                val touchColor = getColor(selected_image, evX, evY)
                val r = touchColor shr 16 and 0xFF
                val g = touchColor shr 8 and 0xFF
                val b = touchColor shr 0 and 0xFF
                rgbcolor = "$r,$g,$b,"
                rgbValue?.setText("RGB Value:  $rgbcolor")
                hexcolor = Integer.toHexString(touchColor)
                if (hexcolor?.length!! > 2) {
                    hexcolor = hexcolor?.substring(2, hexcolor?.length!!)
                }
                if (action == MotionEvent.ACTION_UP) {
                    color_display?.setBackgroundColor(touchColor)
                    hexValue?.setText("HEX Value:  $hexcolor")

                    // Determine color name based on the RGB values
                    val colorName = getColorName(touchColor)
                    colordis?.setText("Color: $colorName")
                }
            } catch (e: Exception) {
            }
            true
        })
    }

    // Define a mapping of RGB ranges to color names
    private fun getColorName(color: Int): String? {
        val r = color shr 16 and 0xFF
        val g = color shr 8 and 0xFF
        val b = color and 0xFF

       return if (r > g && r > b) {
           "It's a shade of red"
           if (r > 200) {
               "Bright or intense red"
           } else {
               "Dark or less intense red"
           }
       } else if (g > r && g > b) {
           "It's a shade of green"
           if (g > 200) {
               "Bright or intense green"
           } else {
               "Dark or less intense green"
           }
       } else if (b > r && b > g) {
           "It's a shade of blue"
           if (b > 200) {
               "Bright or intense blue"
           } else {
               "Dark or less intense blue"
           }
       } else if (r > g && g > b) {
           "It's a shade of yellow"

       } else if (r > b && b > g) {
           "shade of purple"

       } else if (g > b && b > r) {
           "shade of cyan"

       } else {
           "shade of gray or a neutral color"

       }



    }

    private fun getColor(selected_image: ImageView?, evX: Int, evY: Int): Int {
        selected_image!!.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(selected_image.drawingCache)
        selected_image.isDrawingCacheEnabled = false
        return bitmap.getPixel(evX, evY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap
            selected_image.setImageBitmap(imageBitmap)

            // Now you have the captured image in the 'imageBitmap' variable
            // You can further process or save the image as needed.
        } else if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val image = data.data
            selected_image.setImageURI(image)
        }
    }

}

