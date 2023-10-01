package com.example.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundService: Service() {

    val binder=CurrentBinder()

    inner class CurrentBinder : Binder(){

        fun getService():BoundService = this@BoundService

    }

    override fun onBind(intent: Intent?): IBinder =binder

    fun getData():String = "hello i'm bound service"

}
