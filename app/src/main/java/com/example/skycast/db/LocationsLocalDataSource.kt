package com.example.skycast.db

import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow

interface LocationsLocalDataSource {
    suspend fun getAllLocations():Flow<Status>
    suspend fun insertLocation(weatherInfo: WeatherInfo):Long
    suspend fun deleteLocation(weatherInfo: WeatherInfo):Int
}