package com.veiditorg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import androidx.fragment.app.Fragment
import com.example.myapplication.Marketplace
import com.example.veiditorg.R
import com.example.veiditorg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veiditorg.DummyData.RegisteredUsers
import com.veiditorg.DummyData.User

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    companion object {
        // DummyData users:
        private var dummyUser1 = User("User1", "User One Usersson", "user1", "user@user.com", "1231111")
        private var dummyUser2 = User("User2", "User Two Usersson", "user2", "user@user.is", "4562222")
        private var dummyUser3 = User("User3", "User Three Usersson", "user3", "user@user.dk", "7893333")

        // Initialize a list of known users
        // THIS IS WHERE ALL USER DATA IS STORED!
        val allUsers: RegisteredUsers = RegisteredUsers(mutableListOf(dummyUser1, dummyUser2, dummyUser3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LoginFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homepage -> replaceFragment(HomepageFragment())
                R.id.marketplace -> replaceFragment(Marketplace())
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
