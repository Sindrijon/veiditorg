package com.veiditorg

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.Permit
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID


class NewPermitFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val calendar = Calendar.getInstance()

    lateinit var riverInput: EditText
    lateinit var startDateText: TextView
    lateinit var endDateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newpermit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val ownerId = currentUser?.uid ?: ""

        riverInput = view.findViewById<EditText>(R.id.newpermitRiver)
        startDateText = view.findViewById<TextView>(R.id.DateStart)
        endDateText = view.findViewById<TextView>(R.id.DateEnd)

        startDateText.setOnClickListener {
            showDatePicker(startDateText)
        }
        endDateText.setOnClickListener {
            showDatePicker(endDateText)
        }



        val registerBtn: Button = view.findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val river = riverInput.text.toString()
            val startDate = startDateText.text.toString()
            val endDate = endDateText.text.toString()
            val forTrade = false
            val permitID = generatePermitID()

            if (river.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(requireContext(), "Vinsamlegast fyllið út alla reiti.", Toast.LENGTH_SHORT).show()
                if (river.isEmpty()) riverInput.error = "Bætið við á"
                if (startDate.isEmpty()) startDateText.error = "Bætið við byrjunar dagssetningu"
                if (endDate.isEmpty()) endDateText.error = "Bætið við Lokadagssetningu"
                return@setOnClickListener
            }


            if (ownerId.isNotEmpty()) {
                database = FirebaseDatabase.getInstance().getReference("permit")

                val permit = Permit(river, ownerId, forTrade,startDate, endDate,permitID)

                database.child(permitID).setValue(permit).addOnSuccessListener {
                    riverInput.text.clear()
                    startDateText.text = ""
                    endDateText.text = ""

                    Toast.makeText(requireContext(), "Successfully Saved", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showDatePicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                textView.text = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun generatePermitID(): String {
        return UUID.randomUUID().toString()
    }

}

