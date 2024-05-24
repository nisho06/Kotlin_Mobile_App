package com.example.kotlin_mobile_application

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var requestQueue: RequestQueue
private lateinit var landlordTextView: TextView
private lateinit var propertyTextView: TextView
private lateinit var firstNameView: TextView

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var noOfProperties: String? = null
    private var noOfLandlords: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestQueue = Volley.newRequestQueue(context);

        System.out.println("Get Property Landlord count method is calling");

        getPropertyLandlordTotal();

        val firstName = arguments?.getString(ARG_FIRSTNAME)
        val firstNameToDisplay = "Hi " + firstName;

        landlordTextView = view.findViewById<TextView>(R.id.landlordNoText)
        propertyTextView = view.findViewById<TextView>(R.id.propertyNoText)
        firstNameView = view.findViewById<TextView>(R.id.txtFirstName)


        firstNameView.text = firstNameToDisplay;
    }

    companion object {

        private const val ARG_FIRSTNAME = "firstName";
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(firstName: String? ) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_FIRSTNAME, firstName);
                }
            }
    }

    private fun getPropertyLandlordTotal() {
        val url = "http://192.168.1.141/property_rental/auth/getPropertyLandlordTotal.php"
        println("url is this - $url")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println(response)

                noOfProperties = response.getString("noOfProperties");
                noOfLandlords = response.getString("noOfLandlords")

                landlordTextView.text = noOfLandlords
                propertyTextView.text = noOfProperties

            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}