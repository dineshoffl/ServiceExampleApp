package com.example.serviceexampleapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val serviceIntent = Intent(this, ForeGroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        if (intent?.getBooleanExtra("isButtonClicked", false) == true) {
            Log.d("BtnClicked", "Click done")
        }

    }

}