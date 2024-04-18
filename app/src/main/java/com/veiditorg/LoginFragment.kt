package com.veiditorg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import com.example.veiditorg.R
import com.example.veiditorg.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    lateinit var useremailInput : EditText

    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    private var _binding: FragmentLoginBinding? = null


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

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.GONE


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
                notifyUserOfTradeOffers()
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

    private fun notifyUserOfTradeOffers() {
        val currentUser = mAuth.currentUser
        val userId = currentUser?.uid ?: return // Return if user ID is null

        // Reference to the trade offers in the database
        val tradeOffersRef = FirebaseDatabase.getInstance().getReference("tradeoffer")
        tradeOffersRef.orderByChild("initiatingUserId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.children.any()) {
                        // Notify the user if there are any trade offers
                        sendNotification("You have trade offers awaiting your attention!")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("DBError", "loadPost:onCancelled", databaseError.toException())
                }
            })
    }
    private fun sendNotification(message: String) {
        val CHANNEL_ID = "TradeOffersChannel"
        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Building the notification
        val notificationBuilder = NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("New Trade Offer")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // For Oreo and above, Notification Channel is needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Trade Offers",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Issuing the notification
        notificationManager.notify(0, notificationBuilder.build())
    }
}




