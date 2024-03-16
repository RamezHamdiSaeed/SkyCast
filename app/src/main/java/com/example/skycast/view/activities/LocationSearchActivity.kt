package com.example.skycast.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.skycast.R
import com.example.skycast.databinding.ActivityLocationSearchBinding
import com.example.skycast.db.LocationsDB
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.WeatherInfo
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.view.fragments.LocationFavoriteFragment
import com.example.skycast.viewModel.MyViewModel
import com.example.skycast.viewModel.MyViewModelFactory
import com.example.skycast.viewModel.MyViewModelSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationSearchActivity : AppCompatActivity() {
    private val TAG:String="LocationSearchActivity"
    lateinit var binding:ActivityLocationSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocationSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
                    launch {
                        MyViewModelSingleton.sharedViewModel.insertLocation(WeatherInfo("makkah","12.121212","13.22","23","https://openweathermap.org/img/wn/10d@2x.png","good weather"))
                        delay(2000)
                        finish()
                    }
        }
    }
}