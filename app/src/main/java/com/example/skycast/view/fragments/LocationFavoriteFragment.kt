package com.example.skycast.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skycast.R
import com.example.skycast.databinding.FragmentLocationFavoriteBinding

class LocationFavoriteFragment : Fragment() {

private val TAG="LocationFavoriteFragment"
    private lateinit var binding:FragmentLocationFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        val binding=FragmentLocationFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

}