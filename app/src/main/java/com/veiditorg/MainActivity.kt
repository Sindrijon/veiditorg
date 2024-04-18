package com.veiditorg

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import com.example.veiditorg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LoginFragment())


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


}
