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
import com.example.skycast.model.LocationRepositoryImp
import com.example.skycast.network.RemoteDataSource
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.Status
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import kotlinx.coroutines.launch

class LocationInfoFragment : Fragment() {

    private val TAG="LocationInfoFragment"

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
        val view:View = inflater.inflate(R.layout.fragment_location_info, container, false)
        val myViewModel:MyViewModel=ViewModelProvider(this,MyViewModelFactory(LocationRepositoryImp(
            RemoteDataSourceImp()
        ))).get(MyViewModel::class.java)
        myViewModel.getCurrentWeatherConditionsAPI()

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.currentWeatherConditions.collect(){
                    when (it){
                        is Status.Loading ->{
                            Log.d(TAG, "onCreateView: currentWeatherConditions: not retrieved yet")

                        }
                        is Status.Success -> {
                            println(it.weather)
                            Log.d(TAG, "onCreateView: currentWeatherConditions: ${it.weather}")
                        }
                        else ->{
                            Log.d(TAG, "onCreateView: currentWeatherConditions: fail")

                        }
                    }
                }
            }
        }

        return view
    }

}