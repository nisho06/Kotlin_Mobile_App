package com.example.kotlin_mobile_application

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Properties.newInstance] factory method to
 * create an instance of this fragment.
 */
class Properties : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: PropertyAdapter
    private lateinit var requestQueue: RequestQueue
    private var btnAddProperty: Button? = null
    private val emptyList: MutableList<Property> = mutableListOf()

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
        return inflater.inflate(R.layout.fragment_properties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.property_recycler_view)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PropertyAdapter(context, emptyList) // Initially empty list
        recyclerView.adapter = adapter
        requestQueue = Volley.newRequestQueue(context)
        fetchProperties()


        btnAddProperty = view.findViewById(R.id.addProperty);
        val roleName = arguments?.getString(Properties.ARG_ROLE_NAME)
        System.out.println("Role name is " + roleName);

        btnAddProperty = view.findViewById(R.id.addProperty)


        if (roleName == "LANDLORD") {
            btnAddProperty?.setOnClickListener {
                val userId = arguments?.getString(Properties.ARG_USER_ID)
                println("Add Property Button Pressed")
                val intent = Intent(requireContext(), AddProperty::class.java)
                System.out.println("Ithuthaanda user id - " + userId);
                intent.putExtra("userId", userId)
                startActivity(intent)

                // userLogin() // Uncomment this line if userLogin() is defined and needed
            }
        } else {
            btnAddProperty?.visibility = View.GONE // or View.INVISIBLE
        }

        // Initialize and set up the button


    }
    private fun fetchProperties() {
        val url = "http://192.168.1.141/property_rental/auth/properties.php"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val properties = mutableListOf<Property>()
                for (i in 0 until response.length()) {
                    val propertyJson = response.getJSONObject(i)
                    val property = parseProperty(propertyJson)
                    properties.add(property)
                }
                updateRecyclerView(properties)
            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseProperty(json: JSONObject): Property {
        val address = json.getString("address")
        val price = json.getInt("price")
        val bedrooms = json.getInt("no_of_beds")
        val bathrooms = json.getInt("no_of_baths")
        val square_feet = json.getInt("square_feet")
        val property_id = json.getInt("property_id")
        return Property(property_id, address, price, bedrooms, bathrooms, square_feet)
    }

    private fun updateRecyclerView(properties: MutableList<Property>) {
        adapter = PropertyAdapter(context, properties)
        view?.findViewById<RecyclerView>(R.id.property_recycler_view)?.adapter = adapter
    }

    companion object {

        private const val ARG_USER_ID = "userId";
        private const val ARG_ROLE_NAME = "roleName";
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewProperty.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(userId: String?, roleName: String?) =
            Properties().apply {
                arguments = Bundle().apply {

                    if (userId != null) {
                        putString(ARG_USER_ID, userId)
                        putString(ARG_ROLE_NAME, roleName)
                    } else {
                        System.out.println("No user ID available");
                    }

                }
            }
    }
}