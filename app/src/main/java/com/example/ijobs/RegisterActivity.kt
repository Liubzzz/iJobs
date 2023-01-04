package com.example.ijobs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ijobs.R.layout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private lateinit var database: DatabaseReference

@IgnoreExtraProperties


class RegisterActivity : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.register_page)

        database = Firebase.database.reference



        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var et_confirm_password = findViewById(R.id.et_confirm_password) as EditText
        var et_user_email = findViewById(R.id.et_user_email) as EditText
        var btn_register = findViewById(R.id.btn_register) as Button
        var btn_back = findViewById(R.id.btn_back) as Button





        fun registerNewUser() {
            val email= et_user_email.text.toString()
            val user_name= et_user_name.text.toString()
            val password= et_password.text.toString()
            val confirmpassword= et_confirm_password.text.toString()


            if (email.isEmpty()) {
                et_user_email.error = "Please enter a valid email"
            }

            if (user_name.isEmpty()) {
                et_user_name.error = "Please enter user name"
            }

            if (password.isEmpty()) {
                et_password.error = "Please enter a valid password"
            }

            if (confirmpassword.isEmpty()) {
                et_confirm_password.error = "Please enter a matching password"
            }



//            Password must contain at least one digit [0-9].
//            Password must contain at least one lowercase Latin character [a-z].
//            Password must contain at least one uppercase Latin character [A-Z].
//            Password must contain at least one special character like ! @ # & ( ).
//            Password must contain a length of at least 8 characters and a maximum of 20 characters.





            if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$".toRegex()))
                Toast.makeText(this, "password not valid", Toast.LENGTH_LONG).show()

            else

           if(password != confirmpassword && password.isNotEmpty())
               Toast.makeText(this, "passwords don't match", Toast.LENGTH_LONG).show()
           else
           {




            val userId = database.push().key!!


            val user = User(email, user_name, password )

            database.child("users").child(userId).setValue(user)
                .addOnCompleteListener {
                    if(user_name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()){
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    et_user_name.text.clear()
                    et_user_email.text.clear()
                    et_password.text.clear()
                    et_confirm_password.text.clear()}
                    else
                        Toast.makeText(this, "Fields empty", Toast.LENGTH_LONG).show()



                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

        }}







        btn_back.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        btn_register.setOnClickListener {
            registerNewUser()
            //val intent = Intent(this,LoginActivity::class.java)
            //startActivity(intent)


        }
    }
}