package com.example.ijobs

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ijobs.ui.EditProfileDescription
import com.example.ijobs.ui.ProfileCharacteristics

val sendIntent = Intent(Intent.ACTION_VIEW)
class MyAdapter(private val serviceList:ArrayList<Services>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.servicelist_item_design_main_page, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentService= serviceList[position]

        holder.title.text=currentService.serviceTitle
        holder.description.text=currentService.serviceDescription
        holder.phoneNumber.text=currentService.servicePhone
        holder.price.text=currentService.servicePrice
        holder.location.text=currentService.serviceLocation
        holder.jobcallbtn.setOnClickListener{
           ProfileCharacteristics.setPhonenumber(currentService.servicePhone.toString())

        }

    }



    override fun getItemCount(): Int {
        return serviceList.size
    }



    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val title :TextView= itemView.findViewById(R.id.firebase_job_title)
        val description :TextView= itemView.findViewById(R.id.firebase_job_description)
        val phoneNumber :TextView= itemView.findViewById(R.id.firebase_job_phone)
        val price :TextView= itemView.findViewById(R.id.firebase_job_price)
        val location :TextView= itemView.findViewById(R.id.firebase_job_location)
        val jobcallbtn : ImageButton = itemView.findViewById(R.id.job_call_btn)
        val joblocationbtn : ImageButton = itemView.findViewById(R.id.job_location_btn)


    }



}