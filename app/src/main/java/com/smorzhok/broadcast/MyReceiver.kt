package com.smorzhok.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            Intent.ACTION_BATTERY_LOW ->{
                Toast.makeText(context, "Low battery", Toast.LENGTH_SHORT).show()
            }
        }
    }
}