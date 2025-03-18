package com.smorzhok.broadcast


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.Intent.ACTION_BATTERY_LOW
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.smorzhok.broadcast.MyReceiver.Companion.ACTION_CLICKED

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.progress = percent
            }
        }
    }

    private var count = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        findViewById<Button>(R.id.button).setOnClickListener {
            Intent(ACTION_CLICKED).apply {
                putExtra(MyReceiver.EXTRA_COUNT, count++)
                sendBroadcast(this)
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_CLICKED)
            addAction(ACTION_BATTERY_LOW)
            addAction(ACTION_AIRPLANE_MODE_CHANGED)
            addAction("loaded")
        }
        registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        Intent(this, MyService::class.java).apply {
            startService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}