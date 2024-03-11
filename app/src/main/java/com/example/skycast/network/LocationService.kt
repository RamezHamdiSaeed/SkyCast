package com.example.skycast.network

import com.example.skycast.model.LocationInfo
import com.example.skycast.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("reverse")
    suspend fun getLocationInfoByCoordinates(@Query("lat") lat:String="30.5853431",
                                            @Query("lon") long: String = "31.5035127",
                                            @Query("format") format:String="json"): Response<LocationInfo>
}