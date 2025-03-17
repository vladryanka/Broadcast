package com.smorzhok.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val receiver = MyReceiver()

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(MyReceiver.ACTION_CLICKED)
            intent.putExtra(MyReceiver.EXTRA_COUNT, ++count)
            sendBroadcast(intent)

        }
        val filter =
            IntentFilter().apply {
                addAction(Intent.ACTION_BATTERY_LOW)
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
                addAction(MyReceiver.ACTION_CLICKED)
            }
        ContextCompat.registerReceiver(
            this,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}