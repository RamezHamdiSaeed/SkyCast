package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skycast.R
import com.example.skycast.databinding.ActivityLocationSearchBinding

class LocationSearchActivity : AppCompatActivity() {
    lateinit var binding:ActivityLocationSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}