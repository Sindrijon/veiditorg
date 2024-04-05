package com.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.veiditorg.R
import com.example.veiditorg.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun isValidCredentials(username: String, password: String): Boolean {
        //Checks if user exists in list of registered users
        return MainActivity.allUsers.validUser(username, password)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fjarlægir navigationbar frá LoginFragment
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        usernameInput = view.findViewById(R.id.editTextUsername)
        passwordInput = view.findViewById(R.id.editTextPassword)
        loginBtn = view.findViewById(R.id.loginButton)
        val currentActivity = activity

        val textViewSignup: TextView = view.findViewById(R.id.textViewSignup)
        textViewSignup.setOnClickListener {
            if(currentActivity != null) {
                val transaction = currentActivity.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, SignupFragment())
                transaction.commit()
            }
        }

        loginBtn.setOnClickListener {

            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (isValidCredentials(username, password) && currentActivity !=null) {
                    val transaction = currentActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frame_layout, MarketplaceFragment())
                    transaction.commit()
            } else {
                Snackbar.make(view, "Invalid username or password", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




