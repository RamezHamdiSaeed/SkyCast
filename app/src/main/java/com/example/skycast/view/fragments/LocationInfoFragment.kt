package com.example.skycast.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.skycast.R
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.Status
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import kotlinx.coroutines.launch

class LocationInfoFragment : Fragment() {

    private val TAG = "LocationInfoFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        val view: View = inflater.inflate(R.layout.fragment_location_info, container, false)
        val myViewModel: MyViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                LocationWeatherRepositoryImp(
                    RemoteDataSourceImp()
                )
            )
        ).get(MyViewModel::class.java)
        myViewModel.getLocationInfoByCoordinatesAPI()
        myViewModel.getCurrentWeatherConditionsAPI()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myViewModel.currentWeatherConditions.collect { weatherStatus ->
                    when (weatherStatus) {
                        is Status.Loading -> {
                            Log.d(TAG, "onCreateView: currentWeatherConditions: not retrieved yet")
                        }
                        is Status.Success -> {
                            println(weatherStatus.data)
                            Log.d(TAG, "onCreateView: currentWeatherConditions: ${weatherStatus.data}")
                        }
                        else -> {
                            Log.d(TAG, "onCreateView: currentWeatherConditions: fail")
                        }
                    }
                }


            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                myViewModel.locationInfoByCoordinates.collect { locationStatus ->
                    when (locationStatus) {
                        is Status.Loading -> {
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: not retrieved yet")
                        }
                        is Status.Success -> {
                            println(locationStatus.data)
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: ${locationStatus.data}")
                        }
                        else -> {
                            Log.d(TAG, "onCreateView: locationInfoByCoordinates: fail")
                        }
                    }
                }
            }
        }

        return view
    }
}
