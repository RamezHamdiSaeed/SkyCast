package com.example.skycast.network

import com.example.skycast.model.LocationInfo
import com.example.skycast.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("reverse")
    suspend fun getLocationInfoByCoordinates(@Query("lat") lat:String,
                                            @Query("lon") long: String,
                                            @Query("format") format:String="json"): Response<LocationInfo>
}