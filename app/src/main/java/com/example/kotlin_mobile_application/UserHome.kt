package com.example.kotlin_mobile_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_mobile_application.databinding.ActivityUserHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException
import org.json.JSONObject

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
//                    R.id.viewLandlord -> replaceFragment(ViewLandlord())
//                    R.id.viewProperty -> replaceFragment(ViewProperty())
                    R.id.addProperty -> replaceFragment(AddProperty())
                    R.id.addLandlord -> replaceFragment(AddLandlord())

                    else -> {

                    }
                }
                true
            }
    }

//    private fun configureBottomNavigationView(roleName: String?) {
//        val menu = binding.bottomNavigationView.menu
//        when (roleName) {
//            "ADMIN" -> {
//                menu.findItem(R.id.home).isVisible = true
//                menu.findItem(R.id.account).isVisible = true
//                menu.findItem(R.id.notifications).isVisible = false
//                menu.findItem(R.id.viewLandlord).isVisible = true
//                menu.findItem(R.id.addLandlord).isVisible = true
//                menu.findItem(R.id.viewProperty).isVisible = false
//                menu.findItem(R.id.addProperty).isVisible = false
//
//            }
//            "LANDLORD" -> {
//                menu.findItem(R.id.home).isVisible = true
//                menu.findItem(R.id.account).isVisible = true
//                menu.findItem(R.id.notifications).isVisible = true
//                menu.findItem(R.id.viewLandlord).isVisible = false
//                menu.findItem(R.id.addLandlord).isVisible = false
//                menu.findItem(R.id.viewProperty).isVisible = true
//                menu.findItem(R.id.addProperty).isVisible = true
//
//            }
//            else -> {
//                menu.findItem(R.id.home).isVisible = true
//                menu.findItem(R.id.account).isVisible = true
//                menu.findItem(R.id.notifications).isVisible = true
//                menu.findItem(R.id.viewLandlord).isVisible = false
//                menu.findItem(R.id.addLandlord).isVisible = false
//                menu.findItem(R.id.viewProperty).isVisible = false
//                menu.findItem(R.id.addProperty).isVisible = false
//            }
//        }
//    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager;
        val fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}