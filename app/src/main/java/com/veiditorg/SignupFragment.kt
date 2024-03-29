package com.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.veiditorg.R
import com.google.android.material.snackbar.Snackbar
import com.veiditorg.DummyData.User

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : Fragment() {

    lateinit var usernameInput : EditText
    lateinit var fullnameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var emailInput : EditText
    lateinit var phoneInput : EditText
    lateinit var signupBtn : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameInput = view.findViewById<EditText>(R.id.signupUsername)
        fullnameInput = view.findViewById<EditText>(R.id.signupFullname)
        passwordInput = view.findViewById<EditText>(R.id.signupPassword)
        emailInput = view.findViewById<EditText>(R.id.signupEmail)
        phoneInput = view.findViewById<EditText>(R.id.signupPhone)
        signupBtn = view.findViewById<Button>(R.id.signupButton)

        signupBtn.setOnClickListener{
            val username = usernameInput.text.toString()
            val fullname = fullnameInput.text.toString()
            val password = passwordInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()

            saveNewUser(username, fullname, password, email, phone)

            val user = listOf(username, fullname, password, email, phone)
            if (user.contains("")) {
                Snackbar.make(view, "Signup unsuccessful", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, username + " has successfully signed up", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    private fun saveNewUser(username: String, fullname: String, password: String, email: String, phone: String){
        val newUser = User(username, fullname, password, email, phone)
        MainActivity.allUsers.addUser(newUser)
    }
}