package com.example.skycast.network

import com.example.skycast.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
@GET("onecall")
suspend fun getCurrentWeatherConditions(@Query("lat") lat:String="30.5853431",
                                @Query("lon") long: String = "31.5035127",
                                @Query("lang") language:String="ar",
                                @Query("exclude") exclude:String = "minutely,hourly,daily",
                                @Query("units") units:String = "metric",
                                @Query("appid") apiKey:String="8e3540a48d29fcaa9704ffd3b94bad07"):Response<Weather>
}