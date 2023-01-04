package com.example.ijobs


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.ijobs.ui.ProfileCharacteristics





class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_page)

        var showname = findViewById<TextView>(R.id.id_test1)
        var showmail = findViewById<TextView>(R.id.id_test2)
        var sendmessage = findViewById<ImageView>(R.id.id_message)
        var sendlocation = findViewById<ImageView>(R.id.id_location)

        showname.setText(ProfileCharacteristics.getUsername())
        showmail.setText(ProfileCharacteristics.getEmail())

        sendmessage.setOnClickListener{
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:"+"0746235404")
            startActivity(sendIntent)
        }

        sendlocation.setOnClickListener{
            val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode("Valea Timisului"))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

    }
}