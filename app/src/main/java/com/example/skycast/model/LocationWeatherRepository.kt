package com.example.skycast.model

import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow

interface LocationWeatherRepository {
 suspend fun getCurrentLocationWeatherConditionsAPI(lat:String,
                                                    long: String ,
                                                    language:String): Flow<Status>
 suspend fun getCurrentLocationWeatherForcastAPI(lat:String,
                                                       long: String ,
                                                       ): Flow<Status>
 suspend fun getLocationInfoByCoordinatesAPI(lat: String,long: String):Flow<Status>

    suspend fun getAllLocationsDB(): Flow<Status>
    suspend fun insertLocationDB(location: WeatherInfo):Long
    suspend fun deleteLocationDB(location: WeatherInfo):Int
}