package com.example.kotlin_mobile_application

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class AddProperty : AppCompatActivity() {

    private lateinit var edtTxtAddress: TextInputEditText
    private lateinit var edtTxtPostCode: TextInputEditText
    private lateinit var edtTxtSquareFeet: TextInputEditText
    private lateinit var edtTxtPrice: TextInputEditText
    private lateinit var edtTxtNoOfBeds: TextInputEditText
    private lateinit var edtTxtNoOfBathrooms: TextInputEditText
    private lateinit var btnAddProperty: Button
    private var isValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        val userId = intent.extras?.getString("userId")
        Log.d("AddProperty", "User Id is ---- $userId")

        edtTxtAddress = findViewById(R.id.edtTxtAddress)
        edtTxtPostCode = findViewById(R.id.edtTxtPostCode)
        edtTxtSquareFeet = findViewById(R.id.edtTxtSquareFeet)
        edtTxtPrice = findViewById(R.id.edtTxtPrice)
        edtTxtNoOfBeds = findViewById(R.id.edtTxtNoOfBeds)
        edtTxtNoOfBathrooms = findViewById(R.id.edtTxtNoOfBathrooms)
        btnAddProperty = findViewById(R.id.btnAddProperty)


        btnAddProperty!!.setOnClickListener {
            System.out.println("Button Add Property is Pressed");
            addProperty(userId);
        }
    }

    private fun addProperty(userId: String?){
        val propertyAddress = edtTxtAddress.text.toString().trim()
        val postCode = edtTxtPostCode.text.toString().trim()
        val squareFeet = edtTxtSquareFeet.text.toString()
        val price = edtTxtPrice.text.toString().trim()
        val noOfBeds = edtTxtNoOfBeds.text.toString().trim()
        val noOfBaths = edtTxtNoOfBathrooms.text.toString().trim()
        isValid = true;



        if (propertyAddress.isEmpty()) {
            edtTxtAddress.error = "Please enter property address."
            isValid = false
        } else {
            edtTxtAddress.error = null
        }

        if (squareFeet.isEmpty()) {
            edtTxtSquareFeet.error = "Please enter square feet of the property."
            isValid = false
        } else {
            edtTxtSquareFeet.error = null
        }

        if (price.isEmpty()) {
            edtTxtPrice.error = "Please enter the price of the property."
            isValid = false
        } else {
            edtTxtPrice.error = null
        }

        if (noOfBeds.isEmpty()) {
            edtTxtNoOfBeds.error = "Please enter the no of beds of the property."
            isValid = false
        } else {
            edtTxtNoOfBeds.error = null
        }

        if (noOfBaths.isEmpty()) {
            edtTxtNoOfBathrooms.error = "Please enter the no of bathrooms of the property."
            isValid = false
        } else {
            edtTxtNoOfBathrooms.error = null
        }

        if (isValid) {
            Log.d("AddProperty", "User Id: $userId ")
            Log.d("AddProperty", "Property Address: $propertyAddress ")
            Log.d("AddProperty", "Property PostCode: $postCode")
            Log.d("AddProperty", "Property Square Feet: $squareFeet")
            Log.d("AddProperty", "Property Price: $price")
            Log.d("AddProperty", "Property No Of Bedrooms: $noOfBeds")
            Log.d("AddProperty", "Property No Of Bathrooms: $noOfBaths")

            if (userId!=null){
                addPropertyApi(
                    propertyAddress, squareFeet, price, postCode, noOfBeds, noOfBaths, userId);

                AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Property added successfully.")
                    .setPositiveButton(android.R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        // Clear all fields
                        edtTxtAddress.text?.clear()
                        edtTxtPostCode.text?.clear()
                        edtTxtSquareFeet.text?.clear()
                        edtTxtPrice.text?.clear()
                        edtTxtNoOfBeds.text?.clear()
                        edtTxtNoOfBathrooms.text?.clear()
                    }
                    .show()
            }
        }


    }

    private fun addPropertyApi(propertyAddress: String, squareFeet: String, price: String,
                               postCode: String, noOfBeds: String, noOfBaths: String, userId: String){

        val addPropertyRequest = "http://192.168.1.141/property_rental/addProperty.php";

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
                params.put("address", propertyAddress)
                params.put("square_feet", squareFeet )
                params.put("price", price)
                params.put("post_code", postCode)
                params.put("no_of_beds", noOfBeds)
                params.put("no_of_baths", noOfBaths)
                params.put("user_id", userId)
                return params
            }
        }

        System.out.println("Calling the functions Add Property")
        queue.add(stringRequest)
    }
}