package com.example.kotlin_mobile_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlin_mobile_application.databinding.ActivityUserHomeBinding

class UserHome : AppCompatActivity() {

    private lateinit var binding : ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val username = intent.extras?.getString("username")
        val firstName = intent.extras?.getString("firstName")
        val lastName = intent.extras?.getString("lastName")
        val roleName = intent.extras?.getString("roleName")

        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        replaceFragment(Home())

//        configureBottomNavigationView(roleName)

        binding.bottomNavigationView
            .setOnNavigationItemSelectedListener {

                when(it.itemId) {
                    R.id.home -> replaceFragment(Home())
                    R.id.account -> replaceFragment(Account())
                    R.id.notifications -> replaceFragment(Notifications())
                    R.id.landlord -> replaceFragment(Landlord())
                    R.id.property -> replaceFragment(Properties())
                    else -> {

                    }
                }
                true
            }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager;
        val fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}