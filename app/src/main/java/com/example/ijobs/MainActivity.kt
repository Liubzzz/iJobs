package com.example.ijobs

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ijobs.ui.ProfileCharacteristics
import com.google.firebase.database.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private var loop : String? = "0"
    private lateinit var database: DatabaseReference
    private lateinit var serviceRecycerView: RecyclerView
    private lateinit var serviceArrayList: ArrayList<Services>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)


        // get reference to all views

       //-------

        startTimer()

        //-------


        var btn_home = findViewById(R.id.btn_home) as Button
        var btn_profile = findViewById<ImageView>(R.id.btn_profile)
        var btn_add = findViewById(R.id.btn_add) as ImageView


        btn_profile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        btn_add.setOnClickListener {
            val intent = Intent(this, AddAnnouncementActivity::class.java)
            startActivity(intent)
        }



        serviceRecycerView = findViewById(R.id.serviceList)
        serviceRecycerView.layoutManager = LinearLayoutManager(this)
        serviceRecycerView.setHasFixedSize(true)

        serviceArrayList = arrayListOf<Services>()

        getServiceData()




    }

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, action: () -> Unit) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private val timer: Job = startCoroutineTimer(delayMillis = 0, repeatMillis = 2000) {
        Log.d(TAG, "Background - tick")
        if(ProfileCharacteristics.getSignaltransit().toString()=="call")
        {   val intent1 = Intent(Intent.ACTION_VIEW)
            intent1.data = Uri.parse("sms:"+ProfileCharacteristics.getPhonenumber().toString())
          //  cancelTimer()
            ProfileCharacteristics.setSignaltransit("nontransit")
            startActivity(intent1)

        }
        if(ProfileCharacteristics.getSignaltransit().toString()=="location")
        {
            val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(ProfileCharacteristics.getLocation().toString()))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
          //  cancelTimer()
            ProfileCharacteristics.setSignaltransit("nontransit")
            startActivity(mapIntent)

        }

        scope.launch(Dispatchers.Main) {
            Log.d(TAG, "Main thread - tick")

        }
    }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }

    private fun getServiceData() {
        database=FirebaseDatabase.getInstance().getReference("services")

        database.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(serviceSnapshot in snapshot.children){

                            val service = serviceSnapshot.getValue(Services::class.java)
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
