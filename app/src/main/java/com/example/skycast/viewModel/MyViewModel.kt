package com.example.skycast.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycast.utility.Status
import com.example.skycast.model.LocationWeatherRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel(val locationRepositoryImp: LocationWeatherRepositoryImp) : ViewModel() {
    private val TAG = "MyViewModel"

    private val _currentWeatherConditions = MutableStateFlow<Status>(Status.Loading)
    val currentWeatherConditions: StateFlow<Status> = _currentWeatherConditions

    private val _currentWeatherForecast = MutableStateFlow<Status>(Status.Loading)
    val currentWeatherForecast: StateFlow<Status> = _currentWeatherForecast

    private val _locationInfoByCoordinates = MutableStateFlow<Status>(Status.Loading)
    val locationInfoByCoordinates: StateFlow<Status> = _locationInfoByCoordinates

    fun getCurrentWeatherConditionsAPI(lat: String = "30.5853431", long: String = "31.5035127", language: String = "ar") {
        viewModelScope.launch {
            locationRepositoryImp.getCurrentLocationWeatherConditionsAPI(lat, long, language)
                .collect {
                    _currentWeatherConditions.value = it
                }
        }
    }
    fun getCurrentWeatherForcastAPI(lat: String = "30.5853431", long: String = "31.5035127") {
        viewModelScope.launch {
            locationRepositoryImp.getCurrentLocationWeatherForcastAPI(lat, long)
                .collect {
                    _currentWeatherForecast.value = it
                    Log.d(TAG, "getCurrentWeatherForcastAPI: $it")
                }
        }
    }

    fun getLocationInfoByCoordinatesAPI(lat: String = "30.5853431", long: String = "31.5035127") {
        viewModelScope.launch {
            locationRepositoryImp.getLocationInfoByCoordinatesAPI(lat, long)
                .collect {
                    _locationInfoByCoordinates.value = it
                }
        }
    }
}
