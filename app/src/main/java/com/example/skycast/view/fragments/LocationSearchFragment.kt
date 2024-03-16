package com.example.skycast.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skycast.R
import com.example.skycast.databinding.FragmentLocationSearchBinding


class LocationSearchFragment : Fragment() {

    private val TAG="LocationSearchFragment"
    private lateinit var binding:FragmentLocationSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding= FragmentLocationSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}