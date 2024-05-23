package com.example.kotlin_mobile_application

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LandlordAdapter(private val landlords: List<Landlord>) :
    RecyclerView.Adapter<LandlordAdapter.ViewHolder>() {

    // Create a ViewHolder Object which defines the views for a single data. Initially it wont have any data with it.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView
        val no_of_properties: TextView

        init {
            name = view.findViewById(R.id.name);
            no_of_properties = view.findViewById(R.id.no_of_properties)
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
    }

    override fun getItemCount(): Int {
        System.out.println("Size is " + landlords.size);
        return landlords.size
    }

}