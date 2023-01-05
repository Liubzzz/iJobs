//package com.example.ijobs
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.Window
//import android.view.WindowManager
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import com.google.firebase.database.*
//
//class LoginActivity : ComponentActivity() {
//    private var firebaseDatabase : FirebaseDatabase? = null
//    private var databaseReference: DatabaseReference? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.login_page)
//
//        firebaseDatabase = FirebaseDatabase.getInstance()
//        databaseReference = firebaseDatabase?.getReference("users")
//
//
//
//        var username = findViewById(R.id.et_user_name) as EditText
//        var password = findViewById(R.id.et_password) as EditText
//        var btnlogin = findViewById(R.id.btn_login) as Button
//        var btnregister = findViewById(R.id.btn_register) as Button
//
//        btnlogin.setOnClickListener{
//            val intent = Intent(this,MainActivity::class.java)
//            var count : Int? = 0
//            databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(ds in snapshot.children){
//
//                        val id = ds.key
//                        val userName = ds.child("userName").value.toString()
//                        val userPassword = ds.child("userPassword").value.toString()
//                        val userEmail = ds.child("userEmail").value.toString()
//
//                        if(username.text.toString() == userName && password.text.toString() == userPassword){
//                            startActivity(intent)
//                            count = 1
//                        }
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("ooooo","onCancelled: ${error.toException()}")
//                }
//
//            })
//            if(count == 0){
//                // Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//        btnregister.setOnClickListener {
//            val intent = Intent(this,RegisterActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
//
//    private fun getData() {
//        databaseReference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children){
//                    val id = ds.key
//                    val userName = ds.child("userName").value.toString()
//                    val userPassword = ds.child("userPassword").value.toString()
//                    val userEmail = ds.child("userEmail").value.toString()
//
//
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("ooooo","onCancelled: ${error.toException()}")
//            }
//
//        })
//    }
//}




package com.example.ijobs

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ijobs.ui.ProfileCharacteristics
import com.google.firebase.database.*
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.delay

class LoginActivity : ComponentActivity() {
    private var firebaseDatabase : FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("users")


        var count : Int? = 0
        var toast : Toast =  Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT)

        var username = findViewById(R.id.et_user_name) as EditText
        var password = findViewById(R.id.et_password) as EditText
        var btnlogin = findViewById(R.id.btn_login) as Button
        var btnregister = findViewById(R.id.btn_register) as Button

        btnlogin.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(ds in snapshot.children){

                        val id = ds.key
                        val userName = ds.child("userName").value.toString()
                        val userPassword = ds.child("userPassword").value.toString()
                        val userEmail = ds.child("userEmail").value.toString()

                        if(username.text.toString() == userName && password.text.toString() == userPassword){

                            ProfileCharacteristics.setUsername(userName)
                            ProfileCharacteristics.setEmail(userEmail)
                            startActivity(intent)
                            toast.cancel()

                            count = 1

                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ooooo","onCancelled: ${error.toException()}")
                }
            })

                Handler().postDelayed({
                    if(count == 0){
                        toast.show()
                    }
                }, 250)




        }



        btnregister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val id = ds.key
                    val userName = ds.child("userName").value.toString()
                    val userPassword = ds.child("userPassword").value.toString()
                    val userEmail = ds.child("userEmail").value.toString()



                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooooo","onCancelled: ${error.toException()}")
            }

        })
    }
}