package com.example.ijobs


import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ijobs.ui.EditProfileDescription
import com.example.ijobs.ui.ProfileCharacteristics
import com.example.ijobs.ui.ServiceCharacteristics
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.net.URL


class UserProfileActivity : ComponentActivity() {

    private lateinit var  database: DatabaseReference
    private lateinit var  databasedescritpion : DatabaseReference
    private lateinit var serviceRecycerView: RecyclerView
    private lateinit var serviceArrayList:ArrayList<Services>

    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_page)

        var btneditdescription = findViewById<ImageView>(R.id.btn_edit_profile_description)
        var showdescription = findViewById<TextView>(R.id.id_desctiption_profile)
        var showname = findViewById<TextView>(R.id.id_test1)
        var showmail = findViewById<TextView>(R.id.id_test2)
        var sendmessage = findViewById<ImageView>(R.id.id_message)
        var sendlocation = findViewById<ImageView>(R.id.id_location)
        var btn_home = findViewById(R.id.btn_home) as ImageView
        var btn_profile = findViewById<ImageView>(R.id.btn_profile)

        var btn_add = findViewById(R.id.btn_add) as ImageView
        var servicetobedeleted= findViewById<EditText>(R.id.serviceToBeDeleted)
        var btn_delete_service=findViewById<ImageView>(R.id.delete_service)


        showname.setText(ProfileCharacteristics.getUsername())
        showmail.setText(ProfileCharacteristics.getEmail())

        database = Firebase.database.reference

        firebaseDatabase = FirebaseDatabase.getInstance()

        databasedescritpion = FirebaseDatabase.getInstance().getReference("users").child(ProfileCharacteristics.getKey().toString()).child("DescriptionProfile")
        databasedescritpion.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                showdescription.setText(snapshot.getValue().toString())}
                else  {showdescription.setText("No description")}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        btneditdescription.setOnClickListener{
            val intent = Intent(this,EditProfileDescription::class.java)
            startActivity(intent)

        }

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

        btn_home.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btn_add.setOnClickListener {
            val intent = Intent(this,AddAnnouncementActivity::class.java)
            startActivity(intent)
        }




        btn_delete_service.setOnClickListener {
            var serviceTitle= servicetobedeleted.text.toString()
            if(serviceTitle.isNotEmpty()){








                databaseReference = firebaseDatabase?.getReference("services")


                databaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {

                            val id = ds.key.toString()
                            val jobTitle = ds.child("serviceTitle").value.toString()



                            if(serviceTitle==jobTitle)
                                ServiceCharacteristics.setKey(id)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("ooooo", "onCancelled: ${error.toException()}")
                    }
                })
                servicetobedeleted.text.clear()

                deleteService()
            }


            else
                Toast.makeText(this, "Enter a service you want to delete", Toast.LENGTH_LONG).show()

        }









        serviceRecycerView=findViewById(R.id.my_serviceList)
        serviceRecycerView.layoutManager= LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)

        serviceArrayList= arrayListOf<Services>()

        getServiceData()

    }

    private fun getServiceData() {
        database= FirebaseDatabase.getInstance().getReference("services")

        database.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(serviceSnapshot in snapshot.children){

                        val testuser = serviceSnapshot.child("serviceOwner").value.toString()
                        if(testuser == ProfileCharacteristics.getUsername())
                        {
                            val service = serviceSnapshot.getValue(Services::class.java)
                            serviceArrayList.add(service!!)
                        }


                    }

                    serviceRecycerView.adapter=MyAdapter(serviceArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }

    fun deleteService() {






        database = FirebaseDatabase.getInstance().getReference("services")
        database.child(ServiceCharacteristics.getKey().toString()).removeValue().addOnSuccessListener {


            Toast.makeText(this, "Service deleted", Toast.LENGTH_LONG).show()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()

            }




    }



    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}