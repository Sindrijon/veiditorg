package com.example.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_USERNAME_INPUT = "param1"
private const val ARG_FULLNAME_INPUT = "param2"
private const val ARG_PASSWORD_INPUT = "param3"
private const val ARG_EMAIL_INPUT = "param4"
private const val ARG_PHONE_INPUT = "param5"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
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
        usernameInput = view.findViewById(R.id.signupUsername)
        fullnameInput = view.findViewById(R.id.signupFullname)
        passwordInput = view.findViewById(R.id.signupPassword)
        emailInput = view.findViewById(R.id.signupEmail)
        phoneInput = view.findViewById(R.id.signupPhone)
        signupBtn = view.findViewById(R.id.signupButton)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @param param3 Parameter 3.
         * @param param4 Parameter 4.
         * @param param5 Parameter 5.
         * @return A new instance of fragment SignupFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: String, param5: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME_INPUT, param1)
                    putString(ARG_FULLNAME_INPUT, param2)
                    putString(ARG_PASSWORD_INPUT, param3)
                    putString(ARG_EMAIL_INPUT, param4)
                    putString(ARG_PHONE_INPUT, param5)
                }
            }
    }
}