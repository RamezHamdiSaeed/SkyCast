package com.example.skycast.network

import com.example.skycast.model.Weather
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getCurrentWeatherConditions( lat:String,
                                             long: String ,
                                             language:String):Flow<Status>

    suspend fun getLocationInfoByCoordinates(lat:String,
                                             long: String):Flow<Status>
}