package com.veiditorg

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewPermitFragment : Fragment() {
    lateinit var riverInput : EditText
    lateinit var startDateText : TextView
    lateinit var startDateButton : Button
    lateinit var endDateText : TextView
    lateinit var endDateButton : Button
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        riverInput = view.findViewById<EditText>(R.id.newpermitRiver)
        startDateText = view.findViewById<TextView>(R.id.newpermitStartText)
        startDateButton = view.findViewById<Button>(R.id.newpermitStartDate)
        endDateText = view.findViewById<TextView>(R.id.newpermitEndText)
        endDateButton = view.findViewById<Button>(R.id.newpermitEndDate)

        startDateButton.setOnClickListener{
            showDatePicker()
        }
    }

    private fun showDatePicker(){
        // Create a DatePickerDialog
        // Þessi kóði virkar ekki af því DatePickerDialog þarf context en það vill ekki taka við this sem context???
        /*
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                startDateText.text = "Selected Date: $formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
         */
    }
}