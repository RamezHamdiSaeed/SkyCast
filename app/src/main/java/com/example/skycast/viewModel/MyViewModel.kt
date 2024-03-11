package com.example.skycast.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycast.utility.Status
import com.example.skycast.model.LocationRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyViewModel(val locationRepositoryImp:LocationRepositoryImp): ViewModel() {
    private val TAG = "MyViewModel"

    private val _currentWeatherConditions= MutableStateFlow<Status>(Status.Loading)
    val currentWeatherConditions:StateFlow<Status> = _currentWeatherConditions

    fun getCurrentWeatherConditionsAPI(){

        viewModelScope.launch {
           val weather= locationRepositoryImp.getCurrentLocationWeatherConditionsAPI()

                .collect(){
                    _currentWeatherConditions.value=it
                }
        }
    }

}