package com.example.kotlin_mobile_application

import android.annotation.SuppressLint
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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LandlordAdapter(private val context: Context?, private val landlords: MutableList<Landlord>) :
    RecyclerView.Adapter<LandlordAdapter.ViewHolder>() {

    // Create a ViewHolder Object which defines the views for a single data. Initially it wont have any data with it.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView
        val no_of_properties: TextView
        val deleteBtn: Button;


        init {
            name = view.findViewById(R.id.name);
            no_of_properties = view.findViewById(R.id.no_of_properties)
            deleteBtn = view.findViewById(R.id.btnDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandlordAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_landlord, parent, false);
        return ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LandlordAdapter.ViewHolder, position: Int) {
        val landlordAtIndex = landlords[position]
        holder.name.text = landlordAtIndex.firstName + " " + landlordAtIndex.lastName;
        holder.no_of_properties.text = landlordAtIndex.no_of_properties.toString();
        val user_id = landlordAtIndex.user_id;

        holder.deleteBtn.setOnClickListener {
            deleteLandlord(position, user_id);
        }

    }

    private fun deleteLandlord(position: Int, propertyId: Int) {
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

    private fun performDeletion(position: Int, userId: Int) {
        val url = "http://192.168.1.141/property_rental/deleteLandlord.php?id="+ userId;
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                landlords.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, landlords.size)
            },
            { error ->
                Log.e("PropertyAdapter", "Error: ${error.message}")
            }
        )

        requestQueue.add(stringRequest)
    }

    override fun getItemCount(): Int {
        System.out.println("Size is " + landlords.size);
        return landlords.size
    }

}