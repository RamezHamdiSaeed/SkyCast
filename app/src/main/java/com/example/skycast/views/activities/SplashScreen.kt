package com.example.skycast.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.skycast.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : AppCompatActivity() {
    private val TAG="Splash Screen"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                delay(2000)
                withContext(Dispatchers.Main){
                    launch {
                        val intent=Intent(applicationContext,MainActivity::class.java);
                        startActivity(intent)
                    }
                }
            }
        }
    }
}