package com.example.skycast.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.skycast.R
import com.example.skycast.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : AppCompatActivity() {
    private val TAG="Splash Screen"
    lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                        //now when you navigate back from the main activity the app will be closed
                        finish()
                    }
                }
            }
        }
    }
}