package com.veiditorg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.veiditorg.R
import com.example.veiditorg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veiditorg.DummyData.RegisteredUsers

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    companion object {
        // Initialize an empty list of known users
        // THIS IS WHERE USER DATA IS STORED!
        val allUsers: RegisteredUsers = RegisteredUsers(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LoginFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homepage -> replaceFragment(HomepageFragment())
                R.id.marketplace -> replaceFragment(MarketplaceFragment())
               // R.id.addFishingpermit -> replaceFragment(FishingPermitFragment())
                //R.id.trade -> replaceFragment(TradeFragment())
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
