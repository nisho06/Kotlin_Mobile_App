package com.example.kotlin_mobile_application

import android.content.Intent
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
        replaceFragment(Home.newInstance(firstName))

        binding.bottomNavigationView
            .setOnNavigationItemSelectedListener {

                System.out.println("This methods is being called");
                when(it.itemId) {
                    R.id.home -> replaceFragment(Home.newInstance(firstName))
                    R.id.account -> replaceFragment(Account.newInstance(firstName, lastName))
                    R.id.notifications -> replaceFragment(Notifications())
                    R.id.landlord -> replaceFragment(Landlords())
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

    fun navigateToSignIn() {
        // Add code here to navigate to your sign-in screen
        // For example, you might use intents to navigate to another activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}