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

        val registerBtn: Button = view.findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener {
            val river = riverInput.text.toString()
            val startDate = startDateText.text.toString()
            val forTrade = endDateText.text.toString()

            if (ownerId.isNotEmpty()) {
                database = FirebaseDatabase.getInstance().getReference("permit")

                val permit = Permit(river, ownerId, forTrade)

                database.child(ownerId).setValue(permit).addOnSuccessListener {
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
    private fun showDatePicker(textView: TextView){
        // Create a DatePickerDialog
        // Þessi kóði virkar ekki af því DatePickerDialog þarf context en það vill ekki taka við this sem context???

        val datePickerDialog = DatePickerDialog(
            requireContext(), {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                textView.text = "Selected Date: $formattedDate"

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()

    }
}