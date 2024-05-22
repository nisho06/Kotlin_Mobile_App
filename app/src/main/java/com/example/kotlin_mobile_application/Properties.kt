package com.example.kotlin_mobile_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val property1 = Property(
            "123 Main St",
            300000,
            3,
            2,
            1256
        )

        val property2 = Property(
            "37 Fernlea Road",
            35000,
            5,
            1,
            1456
        )

        val property3 = Property(
            "57 Mitcham Road",
            35000,
            5,
            1,
            1456
        )

        val property4 = Property(
            "45 Amamzon Road",
            35000,
            5,
            1,
            1456
        )

        // Initialize your dataset here
        val data = arrayOf(property1, property2, property3, property4);

        val recyclerView = view.findViewById<RecyclerView>(R.id.property_recycler_view)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PropertyAdapter(data)
        recyclerView.adapter = adapter
    }

    companion object {
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
        fun newInstance(param1: String, param2: String) =
            Properties().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}