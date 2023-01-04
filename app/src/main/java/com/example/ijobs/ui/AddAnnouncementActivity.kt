package com.example.ijobs


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var database: DatabaseReference

class AddAnnouncementActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addannouncement_page)

        database = Firebase.database.reference

        var btnback_ios_img = findViewById<ImageView>(R.id.btnback_ios_img)
        var btn_save_announcement = findViewById<ImageView>(R.id.btn_save_announcement)


        var job_title = findViewById(R.id.job_title) as EditText
        var job_description = findViewById(R.id.job_description) as EditText
        var job_phone = findViewById(R.id.job_phone) as EditText
        var job_price = findViewById(R.id.job_price) as EditText
        var job_location = findViewById(R.id.job_location) as EditText









        fun registerNewAnnouncement() {
            val jobtitle= job_title.text.toString()
            val jobdescription= job_description.text.toString()
            val jobphone= job_phone.text.toString()
            val jobprice= job_price.text.toString()
            val joblocation= job_location.text.toString()


            if (jobtitle.isEmpty()) {
                job_title.error = "Please enter a title"
            }

            if (jobdescription.isEmpty()) {
                job_description.error = "Please enter a description"
            }

            if (jobphone.isEmpty()) {
                job_phone.error = "Please enter a valid phone number"
            }
            if (jobprice.isEmpty()) {
                job_price.error = "Please enter a price"
            }

            if (joblocation.isEmpty()) {
                job_location.error = "Please enter an existing location"
            }



//            Password must contain at least one digit [0-9].
//            Password must contain at least one lowercase Latin character [a-z].
//            Password must contain at least one uppercase Latin character [A-Z].
//            Password must contain at least one special character like ! @ # & ( ).
//            Password must contain a length of at least 8 characters and a maximum of 20 characters.




            if(!(jobprice.matches("^[0-9]*\$".toRegex()) && jobphone.matches("^[0-9]*\$".toRegex()) ) )
                Toast.makeText(this, "price or phone number not valid", Toast.LENGTH_LONG).show()

            else

//                if(password != confirmpassword && password.isNotEmpty())
//                    Toast.makeText(this, "passwords don't match", Toast.LENGTH_LONG).show()
//                else
                {




                    val serviceId = database.push().key!!


                    val service = Services(jobtitle, jobdescription, jobphone, jobprice, joblocation )

                    database.child("services").child(serviceId).setValue(service)
                        .addOnCompleteListener {
                            if(jobtitle.isNotEmpty() && jobdescription.isNotEmpty() && jobphone.isNotEmpty()   && jobprice.isNotEmpty() && joblocation.isNotEmpty() ){
                                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                                job_title.text.clear()
                                job_description.text.clear()
                                job_phone.text.clear()
                                job_price.text.clear()
                                job_location.text.clear()
                            }
                            else
                                Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()



                        }.addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }

                }
    }













        btn_save_announcement.setOnClickListener{
            registerNewAnnouncement()

        }

        btnback_ios_img.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}