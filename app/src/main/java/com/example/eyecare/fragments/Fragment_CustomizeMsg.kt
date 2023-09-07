package com.example.eyecare.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.eyecare.R
import com.example.eyecare.activities.EmergencyContactModuleActivity
import com.example.eyecare.activities.EyeGuardianServiceActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_CustomizeMsg.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_CustomizeMsg : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customize_msg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDone = view.findViewById<Button>(R.id.navDoneBtnCustMsg)
        val parentActivity = activity as? EyeGuardianServiceActivity

        var emergMsg: String


        btnDone?.setOnClickListener(){
            //From this function you should get the entered details and then load the next fragment
            emergMsg = view?.findViewById<EditText>(R.id.edtTxtCustomMsg)?.text.toString()
            parentActivity?.saveCustomMsg(emergMsg)
            //Calling the emergency home page
            parentActivity?.callEmergHome()

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_CustomizeMsg.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_CustomizeMsg().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}