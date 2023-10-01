package com.example.boundservice

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnStart: Button
    private var boundService: BoundService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val binder = service as BoundService.CurrentBinder
            boundService = binder.getService()
            isBound = true

        }
        override fun onServiceDisconnected(name: ComponentName?) {

            isBound=false

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceIntent=Intent(this,BoundService::class.java)
        bindService(serviceIntent,connection,Context.BIND_AUTO_CREATE)
        btnStart=findViewById(R.id.btnStart)
        btnStart.setOnClickListener {

            boundService?.getData()

        }

    }

    override fun onDestroy() {

        super.onDestroy()
        if (isBound){

            unbindService(connection)
            isBound=false

        }

    }

    fun getDataFromService(){

        if (isBound){

            val data= boundService?.getData()
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show()

        }

    }

}