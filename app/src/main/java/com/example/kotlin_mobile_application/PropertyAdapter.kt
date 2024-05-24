package com.example.kotlin_mobile_application

import android.app.AlertDialog
import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class PropertyAdapter(private val context: Context?, private val properties: MutableList<Property>) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    // Create a ViewHolder Object which defines the views for a single data. Initially it wont have any data with it.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val property_image : ImageView
        val property_price: TextView
        val property_address: TextView
        val no_of_beds: TextView
        val no_of_bath: TextView
        val square_feet: TextView
        val deleteBtn: Button;

        init {
            property_image = view.findViewById(R.id.property_image);
            property_price = view.findViewById(R.id.property_price)
            property_address = view.findViewById(R.id.property_address)
            no_of_beds = view.findViewById(R.id.no_of_beds)
            no_of_bath = view.findViewById(R.id.no_of_bath)
            square_feet = view.findViewById(R.id.square_feet)
            deleteBtn = view.findViewById(R.id.btnDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_property, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: PropertyAdapter.ViewHolder, position: Int) {
        val propertyAtIndex = properties[position]
        holder.property_address.text = propertyAtIndex.property_address;
        holder.property_price.text = propertyAtIndex.property_price.toString();
        holder.no_of_beds.text = propertyAtIndex.property_beds.toString();
        holder.no_of_bath.text = propertyAtIndex.property_baths.toString();
        holder.square_feet.text = propertyAtIndex.property_square_feet.toString();
        val propertyId = propertyAtIndex.propertyId;

        holder.deleteBtn.setOnClickListener {
            deleteProperty(position, propertyId);
        }

    }

    private fun deleteProperty(position: Int, propertyId: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle("Confirm Deletion")
            setMessage("Are you sure you want to delete this property?")
            setPositiveButton("Ok") { dialog, which ->
                // User clicked OK button
                performDeletion(position, propertyId)
            }
            setNegativeButton("Cancel") { dialog, which ->
                // User clicked Cancel button
                dialog.dismiss()
            }
            show()
        }
    }

    override fun getItemCount(): Int {
        System.out.println("Size is " + properties.size);
        return properties.size
    }

    private fun performDeletion(position: Int, propertyId: Int) {
        val url = "http://192.168.1.141/property_rental/deleteProperty.php?id="+ propertyId;
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                properties.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, properties.size)
            },
            { error ->
                Log.e("PropertyAdapter", "Error: ${error.message}")
            }
        )

        requestQueue.add(stringRequest)
    }

}