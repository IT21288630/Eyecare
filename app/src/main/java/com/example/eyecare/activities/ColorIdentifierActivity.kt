package com.example.eyecare.activities

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.eyecare.R

class ColorIdentifierActivity : AppCompatActivity() {

     lateinit var selected_image : ImageView


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
                rgbValue?.setText("RGB  $rgbcolor")
                hexcolor = Integer.toHexString(touchColor)
                if (hexcolor?.length!! > 2) {
                    hexcolor = hexcolor?.substring(2, hexcolor?.length!!)
                }
                if (action == MotionEvent.ACTION_UP) {
                    color_display?.setBackgroundColor(touchColor)
                    hexValue?.setText("HEX  $hexcolor")

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
    private fun getColorName(color: Int): String {
        val r = color shr 16 and 0xFF
        val g = color shr 8 and 0xFF
        val b = color and 0xFF
        return if (r >= 150 && g <= 50 && b <= 50) {
            "Red"
        } else if (r >= 200 && g >= 100 && b <= 50) {
            "Orange"
        } else if (r >= 200 && g >= 200 && b <= 50) {
            "Yellow"
        } else if (r <= 50 && g >= 200 && b <= 50) {
            "Green"
        } else if (r <= 50 && g <= 50 && b >= 200) {
            "Blue"
        } else if (r <= 75 && g <= 0 && b >= 130) {
            "Indigo"
        } else if (r >= 100 && g <= 100 && b >= 200) {
            "Violet"
        } else {
            "Unknown"
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val Image = data.data
            selected_image!!.setImageURI(Image)
        }
    }
}

