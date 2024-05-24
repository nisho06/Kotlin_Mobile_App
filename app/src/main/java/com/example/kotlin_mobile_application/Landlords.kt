package com.example.kotlin_mobile_application

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Landlords.newInstance] factory method to
 * create an instance of this fragment.
 */
class Landlords : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: LandlordAdapter
    private lateinit var requestQueue: RequestQueue
    private var btnAddLandlord: Button? = null
    private val emptyList: MutableList<Landlord> = mutableListOf()


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
        return inflater.inflate(R.layout.fragment_landlords, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.landlord_recycler_view)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LandlordAdapter(context, emptyList) // Initially empty list
        recyclerView.adapter = adapter
        requestQueue = Volley.newRequestQueue(context)
        fetchLandlords();

        btnAddLandlord = view.findViewById(R.id.addLandlord);

        // Initialize and set up the button
        btnAddLandlord?.setOnClickListener {
            println("Add Landlord Button Pressed")
            val intent = Intent(requireContext(), AddLandlord::class.java)
            startActivity(intent)

            // userLogin() // Uncomment this line if userLogin() is defined and needed
        }

    }

    private fun fetchLandlords() {
        val url = "http://192.168.1.141/property_rental/auth/landlords.php"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val landlords = mutableListOf<Landlord>()
                for (i in 0 until response.length()) {
                    val landlordJson = response.getJSONObject(i)
                    val landlord = parseLandlord(landlordJson)
                    landlords.add(landlord)
                }
                System.out.println(landlords);
                updateRecyclerView(landlords)
            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseLandlord(json: JSONObject): Landlord {
        val firstName = json.getString("first_name")
        val lastName = json.getString("last_name")
        val no_of_properties = json.getInt("property_count")
        val user_id = json.getInt("user_id")
        return Landlord(user_id, firstName, lastName, no_of_properties);
    }

    private fun updateRecyclerView(landlords: MutableList<Landlord>) {
        adapter = LandlordAdapter(context, landlords)
        view?.findViewById<RecyclerView>(R.id.landlord_recycler_view)?.adapter = adapter
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewLandlord.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Landlords().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}