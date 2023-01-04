package com.example.ijobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {

    private lateinit var  database: DatabaseReference
    private lateinit var serviceRecycerView:RecyclerView
    private lateinit var serviceArrayList:ArrayList<Services>


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



        serviceRecycerView=findViewById(R.id.serviceList)
        serviceRecycerView.layoutManager=LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)

        serviceArrayList= arrayListOf<Services>()

        getServiceData()






        }

    private fun getServiceData() {
        database=FirebaseDatabase.getInstance().getReference("services")

        database.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(serviceSnapshot in snapshot.children){

                        val service= serviceSnapshot.getValue(Services::class.java)
                        serviceArrayList.add(service!!)


                    }

                    serviceRecycerView.adapter=MyAdapter(serviceArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }
}
