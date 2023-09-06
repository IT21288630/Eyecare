package com.example.eyecare.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import com.example.eyecare.R
import com.example.eyecare.database.EyecareDatabase
import com.example.eyecare.database.entities.EmergencyDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateEmergencyDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_emergency_details)

        var edtMyName = findViewById<EditText>(R.id.edtEmrgName)
        var edtMyContNo1 = findViewById<EditText>(R.id.edtEmrgNo1)
        var edtMyContNo2 = findViewById<EditText>(R.id.edtEmrgNo2)
        var edtMyContNo3 = findViewById<EditText>(R.id.edtEmrgNo3)
        var edtMyMsg = findViewById<EditText>(R.id.edtEmrgUpd)

        val saveBtn = findViewById<Button>(R.id.btnUpdEmrgDetails)

        GlobalScope.launch(Dispatchers.IO) {
            val dataList: List<EmergencyDetails> = getEmergencyDetails()
            launch(Dispatchers.Main) {
                // Update UI on the main thread
                edtMyName.text = Editable.Factory.getInstance().newEditable( dataList.getOrNull(0)?.uName ?: "Emergency name is not available yet")
                edtMyContNo1.text = Editable.Factory.getInstance().newEditable(dataList.getOrNull(0)?.contNo1 ?: "Not available" )
                edtMyContNo2.text = Editable.Factory.getInstance().newEditable(dataList.getOrNull(0)?.contNo2 ?: "Not available")
                edtMyContNo3.text = Editable.Factory.getInstance().newEditable(dataList.getOrNull(0)?.contNo3 ?: "Not available")
                edtMyMsg.text = Editable.Factory.getInstance().newEditable(dataList.getOrNull(0)?.emergMsg ?: "Emergency message is not available")
            }
        }

        saveBtn.setOnClickListener(){
            //Update entity
            val goBackIntent = Intent(this, EmergencyContactModuleActivity::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val data: List<EmergencyDetails> = getEmergencyDetails()

                val updatedData = EmergencyDetails(
                    id = data[0].id,
                    uName = edtMyName.text.toString(),
                    contNo1 = edtMyContNo1.text.toString(),
                    contNo2 = edtMyContNo2.text.toString(),
                    contNo3 = edtMyContNo3.text.toString(),
                    emergMsg = edtMyMsg.text.toString()
                )

                updateEmergDetails(updatedData)

                // Start the activity on the main thread after the update is complete
                withContext(Dispatchers.Main) {
                    startActivity(goBackIntent)
                }
            }
        }
    }

    suspend fun getEmergencyDetails(): List<EmergencyDetails> {

        val db = EyecareDatabase.getInstance(this@UpdateEmergencyDetailsActivity)
        val emrgDao = db.getEmergencyDetails()

        return emrgDao.getAll()

    }

    suspend fun updateEmergDetails(data : EmergencyDetails){
        val db = EyecareDatabase.getInstance(this@UpdateEmergencyDetailsActivity)
        val emrgDao = db.getEmergencyDetails()

        emrgDao.update(data)
    }
}