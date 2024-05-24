package com.example.kotlin_mobile_application

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    private var editTxtUsername: EditText? = null
    private var editTxtPassword: EditText? = null
    private var errorTxt: TextView? = null
    private var btnLogin: Button? = null

    var authentication_url = "http://192.168.1.141/property_rental/auth/auth.php";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTxtUsername = findViewById<EditText>(R.id.editTxtUsername);
        editTxtPassword = findViewById<EditText>(R.id.editTxtPassword);
        errorTxt = findViewById<TextView>(R.id.errorTxt);
        btnLogin = findViewById<Button>(R.id.btnLogin);

        btnLogin!!.setOnClickListener {
            System.out.println("Button Pressed");
            userLogin();
        }
    }


    private fun userLogin(){


        var strUsername = editTxtUsername!!.text.toString()
        var strPassword = editTxtPassword!!.text.toString()
        val queue = Volley.newRequestQueue(this);
        System.out.println("Username is " + strUsername + " and " + " Password is " + strPassword);
        val stringRequest = object : StringRequest(
            Method.POST,
            authentication_url,
            Response.Listener<String>{ response ->
                try {
                    System.out.println(response);
                    val obj = JSONObject(response)

                    val loginStatus = obj.getString("status");
                    System.out.println("Login status is "+loginStatus);



                    if (loginStatus.equals("success")){
                        val userInformation = JSONObject(obj.getString("userInformation"));
                        val dataInformation = JSONObject(obj.getString("dataInformation"));

                        val intent = Intent(this, UserHome::class.java)
                        intent.putExtra("username", strUsername)
                        intent.putExtra("firstName", userInformation.getString("firstName"));
                        intent.putExtra("lastName", userInformation.getString("lastName"));
                        intent.putExtra("roleName", userInformation.getString("roleName"));
                        intent.putExtra("noOfProperties", dataInformation.getString("noOfProperties"));
                        intent.putExtra("noOfLandlords", dataInformation.getString("noOfLandlords"));
                        startActivity(intent)
                    } else {
                        showCustomDialog();
                    }

                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }catch (e: JSONException){
                    e.printStackTrace()
                }
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
                params.put("username", strUsername)
                params.put("password", strPassword)
                return params
            }
        }

        System.out.println("Calling the functions..")
        queue.add(stringRequest)
    }

    private fun showCustomDialog() {
        // Inflate the custom dialog view
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        // Create and show the dialog
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        // Get the dialog components
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        // Set dialog components values
        dialogTitle.text = "Alert"
        dialogMessage.text = "Incorrect Username/Password combination"

        // Set button click listener
        dialogButton.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }
    }
}
