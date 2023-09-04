package com.example.eyecare.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.eyecare.R
import com.example.eyecare.activities.EyeGuardianServiceActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_getContactNumbers.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_getContactNumbers : Fragment() {
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
        return inflater.inflate(R.layout.fragment_get_contact_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNextTwo = view.findViewById<Button>(R.id.navNxtBtnContNo)
        val parentActivity = activity as? EyeGuardianServiceActivity
        var emergContact1: String
        var emergContact2: String
        var emergContact3: String


        btnNextTwo?.setOnClickListener(){
            //From this function you should get the entered details and then load the next fragment
            emergContact1 = view?.findViewById<EditText>(R.id.getContactNo1Edt).toString()
            emergContact2 = view?.findViewById<EditText>(R.id.getContactNo2Edt).toString()
            emergContact3 = view?.findViewById<EditText>(R.id.getContactNo3Edt).toString()

            parentActivity?.saveEmergNo(emergContact1,emergContact2,emergContact3)
            //Calling the next fragment
            parentActivity?.callCustomMsgFrag()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_getContactNumbers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_getContactNumbers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}