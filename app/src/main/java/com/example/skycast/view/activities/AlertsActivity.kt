package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skycast.R
import com.example.skycast.databinding.ActivityAlertsBinding

class AlertsActivity : AppCompatActivity() {
    lateinit var binding:ActivityAlertsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAlertsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}