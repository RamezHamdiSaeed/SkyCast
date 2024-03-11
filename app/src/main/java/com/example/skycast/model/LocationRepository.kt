package com.example.skycast.model

import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
 suspend fun getCurrentLocationWeatherConditionsAPI(): Flow<Status>
}