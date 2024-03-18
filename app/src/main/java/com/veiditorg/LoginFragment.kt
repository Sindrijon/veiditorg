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

    private val validCredentials = arrayOf(
        Pair("user1", "password1"),
        Pair("user2", "password2"),
        Pair("user3", "password3")
    )

    private fun isValidCredentials(username: String, password: String): Boolean {
        return validCredentials.any { it.first == username && it.second == password }
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

        usernameInput = view.findViewById(R.id.editTextUsername)
        passwordInput = view.findViewById(R.id.editTextPassword)
        loginBtn = view.findViewById(R.id.loginButton)


        val textViewSignup: TextView = view.findViewById(R.id.textViewSignup)
        textViewSignup.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SignupFragment)
        }

        loginBtn.setOnClickListener {

            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (isValidCredentials(username, password)) {
                //fara á userhomepage
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

