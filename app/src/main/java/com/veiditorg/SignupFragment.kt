package com.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.veiditorg.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText
    lateinit var passwordInput2 : EditText
    lateinit var signupBtn : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        //fjarlægir navigtion bar frá signupFragment
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE


        emailInput = view.findViewById<EditText>(R.id.signupEmail)
        passwordInput = view.findViewById<EditText>(R.id.signupPassword)
        passwordInput2 = view.findViewById<EditText>(R.id.signupPasswordConfirm)
        signupBtn = view.findViewById<Button>(R.id.signupButton)


        signupBtn.setOnClickListener{

            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val password2 = passwordInput2.text.toString()

            val currentActivity = activity


            if (email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {
                if (password == password2) {

                    registerUser(email, password)

                } else {
                    Toast.makeText(context, "Lykilorð ekki eins", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(context, "Engir tómir reitir!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }


    private fun registerUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, LoginFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                // Display an error message if registration fails
                Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
    }

}