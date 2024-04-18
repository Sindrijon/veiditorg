package com.veiditorg

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import com.example.veiditorg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.TradeOffer

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val ownerId = currentUser?.uid ?: ""

        if (ownerId == null) {
            replaceFragment(LoginFragment())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)!=
            PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),101)
            }
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    replaceFragment(LoginFragment())
                    true
                }
                R.id.deleteUser -> {
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        user.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("UserDeletion", "Account Deleted")
                                    FirebaseAuth.getInstance().signOut()
                                    replaceFragment(LoginFragment())
                                    Toast.makeText(this, "Account successfully deleted.", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.e("UserDeletion", "Failed to delete user account", task.exception)
                                    Toast.makeText(this, "Failed to delete account. Please try again.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        true
                    } else {
                        Toast.makeText(this, "No user logged in.", Toast.LENGTH_SHORT).show()
                        false
                    }
                }
                else -> false
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homepage -> replaceFragment(HomepageFragment())
                R.id.marketplace -> replaceFragment(MarketplaceFragment())
                R.id.addFishingpermit -> replaceFragment(NewPermitFragment())
                R.id.trade -> replaceFragment(TradeFragment())
            }
            true
        }



        val database = FirebaseDatabase.getInstance()
        val permitRef = database.getReference("tradeoffer")
        permitRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tradeOffer = snapshot.getValue(TradeOffer::class.java)
/*
                Log.d("Notification", "þetta er að updatast")
                if(tradeOffer ==null){
                    Log.e("Notification", "night good")
                    return
                }
 */
                Log.d("Notification", "TradeOffer data: $tradeOffer")

               // Check if the new offer belongs to the current user
                if (tradeOffer !=null && tradeOffer.respondingUserId==ownerId) {
                    // Notify user about the new offer

                    sendNotification("New offer received!")
                   Log.d("Notification", "TradeOffer data: $tradeOffer")
               }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bottomnavigation_bar, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    private fun sendNotification(message: String) {
        val CHANNEL_ID = "TradeOffersChannel"


        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("New Trade Offer")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Create notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Trade Offers",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }



}
