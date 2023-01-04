package com.example.ijobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        // get reference to all views

        var btn_home = findViewById(R.id.btn_home) as Button
        var btn_profile = findViewById<ImageView>(R.id.btn_profile)
        var btn_add = findViewById(R.id.btn_add) as ImageView


        btn_home.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


        btn_profile.setOnClickListener {
            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
            }

        btn_add.setOnClickListener {
            val intent = Intent(this,AddAnnouncementActivity::class.java)
            startActivity(intent)
        }

        }
}
