package com.veiditorg

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import com.example.veiditorg.databinding.FragmentNewpermitBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.Permit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class NewPermitFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    lateinit var riverInput: EditText
    lateinit var startDateText: TextView
    lateinit var endDateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_newpermit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your views here
        riverInput = view.findViewById<EditText>(R.id.newpermitRiver)
        startDateText = view.findViewById<TextView>(R.id.DateStart)
        endDateText = view.findViewById<TextView>(R.id.DateEnd)

        val registerBtn: Button = view.findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener {
            // Now that riverInput, startDateText, and endDateText have been initialized, you can use them here

            // Extract the text values from your inputs
            val river = riverInput.text.toString()
            val ownerId = startDateText.text.toString() // Using start date as ownerId
            val forTrade = endDateText.text.toString() // Using end date as forTrade

            // Initialize your Firebase database reference
            database = FirebaseDatabase.getInstance().getReference("permit")

            // Create a new Permit instance with the input values
            val permit = Permit(river, ownerId, forTrade)

            // Use ownerId as the child name for this permit
            database.child(ownerId).setValue(permit).addOnSuccessListener {
                // Clear the input fields after successful save
                riverInput.text.clear()
                startDateText.text = ""
                endDateText.text = ""

                Toast.makeText(requireContext(), "Successfully Saved", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}