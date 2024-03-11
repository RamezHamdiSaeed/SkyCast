package com.example.skycast.network

import com.example.skycast.model.Weather
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getCurrentWeatherConditions():Flow<Status>
}