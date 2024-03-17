package com.example.skycast.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.skycast.R
import com.example.skycast.databinding.ActivityLocationInfoBinding
import com.example.skycast.view.fragments.LocationInfoFragment
import com.example.skycast.view.fragments.LocationSearchFragment

class LocationInfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityLocationInfoBinding
    private  val TAG="LocationInfoActivity"
    private lateinit var locationInfoFragment: LocationInfoFragment
    private lateinit var fragmentMngr: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationInfoFragment= LocationInfoFragment(intent.getStringExtra("latitude"),intent.getStringExtra("longitude"),true)
        fragmentMngr=supportFragmentManager
        fragmentTransaction=fragmentMngr.beginTransaction()
        fragmentTransaction.replace(binding.infoFragmentContainerView.id,locationInfoFragment,"Dynamic Injection")
        fragmentTransaction.commit()
    }
}