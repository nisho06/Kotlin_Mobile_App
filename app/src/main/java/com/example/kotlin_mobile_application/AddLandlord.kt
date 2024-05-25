package com.example.kotlin_mobile_application

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class AddLandlord : AppCompatActivity() {

    private lateinit var edtTxtFirstName: TextInputEditText
    private lateinit var edtTxtLastName: TextInputEditText
    private lateinit var edtTxtUsername: TextInputEditText
    private lateinit var edtTxtPassword: TextInputEditText
    private lateinit var edtTxtConfirmPassword: TextInputEditText
    private lateinit var btnAddLandlord: Button
    private var isValid = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_landlord)

        edtTxtFirstName = findViewById(R.id.edtTxtFirstName)
        edtTxtLastName = findViewById(R.id.edtTxtLastName)
        edtTxtUsername = findViewById(R.id.edtTxtUsername)
        edtTxtPassword = findViewById(R.id.edtTxtPassword)
        edtTxtConfirmPassword = findViewById(R.id.edtTxtConfirmPassword)
        btnAddLandlord = findViewById(R.id.btnAddLandlord)

        btnAddLandlord!!.setOnClickListener {
            System.out.println("Button Add Property is Pressed");
            addLandlord();
        }
    }

    private fun addLandlord(){
        val firstName = edtTxtFirstName.text.toString().trim()
        val lastName = edtTxtLastName.text.toString().trim()
        val username = edtTxtUsername.text.toString()
        val password = edtTxtPassword.text.toString().trim()
        val confirmPassword = edtTxtConfirmPassword.text.toString().trim()

        isValid = true

        if (firstName.isEmpty()) {
            edtTxtFirstName.error = "Please enter property address."
            isValid = false
        } else {
            edtTxtFirstName.error = null
        }

        if (username.isEmpty()) {
            edtTxtUsername.error = "Please enter the price of the property."
            isValid = false
        } else {
            edtTxtUsername.error = null
        }

        if (password.isEmpty()) {
            edtTxtPassword.error = "Please enter the no of beds of the property."
            isValid = false
        } else {
            edtTxtPassword.error = null
        }

        if (confirmPassword.isEmpty()) {
            edtTxtConfirmPassword.error = "Please enter the no of bathrooms of the property."
            isValid = false
        } else {
            edtTxtConfirmPassword.error = null
        }

        if (password != confirmPassword) {
            edtTxtConfirmPassword.error = "Passwords do not match."
            isValid = false
        }


        if (isValid) {
            Log.d("AddLandlord", "First Name: $firstName ")
            Log.d("AddLandlord", "Last Name: $lastName ")
            Log.d("AddLandlord", "Username: $username")

            checkUsernameExist(username);

            if (isValid){
                addLandlord(firstName, lastName, username, password);
                AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Landlord added successfully.")
                    .setPositiveButton(android.R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        // Clear all fields
                        edtTxtFirstName.text?.clear()
                        edtTxtLastName.text?.clear()
                        edtTxtUsername.text?.clear()
                        edtTxtPassword.text?.clear()
                        edtTxtConfirmPassword.text?.clear()
                    }
                    .show()
            }

        }



    }

    private fun checkUsernameExist(userName: String){
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.141/property_rental/isUsernameExist.php?username=" + userName;
        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            { response ->
                // Response from PHP script
                val isUsernameAlreadyExists = response.toBoolean() // Convert response to boolean
                if (isUsernameAlreadyExists){
                    edtTxtUsername.error = "Username is already taken"
                    isValid = false
                } else {
                    isValid = true
                }
            },
            { error ->
                // Error handling
                error.printStackTrace()
            }
        ) {
        }
        queue.add(stringRequest)
    }


    private fun addLandlord(firstName: String, lastName: String, username: String,
                               password: String){

        val addPropertyRequest = "http://192.168.1.141/property_rental/addLandlord.php";

        val queue = Volley.newRequestQueue(this);
        val stringRequest = object : StringRequest(
            Method.POST,
            addPropertyRequest,
            Response.Listener<String>{ response ->
                System.out.println("Query Executed");
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    if (error != null) {
                        Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                    };
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("firstName", firstName)
                params.put("lastName", lastName )
                params.put("username", username)
                params.put("password", password)
                return params
            }
        }
        System.out.println("Calling the functions Add Landlord")
        queue.add(stringRequest)
    }


}