package com.example.ijobs.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.example.ijobs.LoginActivity
import com.example.ijobs.R
import com.example.ijobs.UserProfileActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference

class EditProfileDescription : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile_page)

        database = Firebase.database.reference

        var edittextprofile = findViewById(R.id.edit_profile_description) as EditText
        var btnsavedescription = findViewById<Button>(R.id.btn_save_description)
        var btnback = findViewById<ImageView>(R.id.btn_back_to_profile)

        btnsavedescription.setOnClickListener{
            database.child("users").child(ProfileCharacteristics.getKey().toString()).child("DescriptionProfile").setValue(edittextprofile.text.toString())
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        btnback.setOnClickListener{
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

    }

}