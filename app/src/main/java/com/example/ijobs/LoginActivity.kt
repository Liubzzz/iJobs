package com.example.ijobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // get reference to all views
        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_register = findViewById(R.id.btn_register) as Button
        var btn_login = findViewById(R.id.btn_login) as Button

        btn_register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        // set on-click listener
        btn_login.setOnClickListener {


            //val user_name = et_user_name.text;
            //val password = et_password.text;
            //Toast.makeText(this@LoginActivity, user_name, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }
}
