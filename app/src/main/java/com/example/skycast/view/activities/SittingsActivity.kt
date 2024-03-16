package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skycast.R
import com.example.skycast.databinding.ActivitySittingsBinding

class SittingsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySittingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySittingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}