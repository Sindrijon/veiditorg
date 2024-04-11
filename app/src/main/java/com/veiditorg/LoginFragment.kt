package com.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.veiditorg.R
import com.example.veiditorg.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    lateinit var useremailInput : EditText

    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        //fjarlægir navigationbar frá LoginFragment
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        useremailInput = view.findViewById(R.id.editTextUsername)
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

            val email = useremailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())

                loginUser(email, password)
            else
                Toast.makeText(context, "Fylla út email og pw", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {task->
            if (task.isSuccessful) {

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, MarketplaceFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
    }
}




