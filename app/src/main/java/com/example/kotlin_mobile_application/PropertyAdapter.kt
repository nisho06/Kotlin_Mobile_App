package com.example.kotlin_mobile_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PropertyAdapter(private val properties: List<Property>) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    // Create a ViewHolder Object which defines the views for a single data. Initially it wont have any data with it.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val property_image : ImageView
        val property_price: TextView
        val property_address: TextView
        val no_of_beds: TextView
        val no_of_bath: TextView
        val square_feet: TextView

        init {
            property_image = view.findViewById(R.id.property_image);
            property_price = view.findViewById(R.id.property_price)
            property_address = view.findViewById(R.id.property_address)
            no_of_beds = view.findViewById(R.id.no_of_beds)
            no_of_bath = view.findViewById(R.id.no_of_bath)
            square_feet = view.findViewById(R.id.square_feet)
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

    }

    override fun getItemCount(): Int {
        System.out.println("Size is " + properties.size);
        return properties.size
    }

}