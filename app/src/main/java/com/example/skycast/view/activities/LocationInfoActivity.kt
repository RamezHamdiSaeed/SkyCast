package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skycast.R
import com.example.skycast.databinding.ActivityLocationInfoBinding

class LocationInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityLocationInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}