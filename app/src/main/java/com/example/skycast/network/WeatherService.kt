package com.example.skycast.network

import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
@GET("onecall")
suspend fun getCurrentWeatherConditions(@Query("lat") lat:String,
                                @Query("lon") long: String ,
                                @Query("lang") language:String,
                                @Query("exclude") exclude:String = "minutely,hourly,daily",
                                @Query("units") units:String = "metric",
                                @Query("appid") apiKey:String="8e3540a48d29fcaa9704ffd3b94bad07"):Response<Weather>
//    @GET("forecast")
//    suspend fun getCurrentWeatherForecast(
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
//        @Query("appid") apiKey: String = "8e3540a48d29fcaa9704ffd3b94bad07"
//    ): Response<WeatherForecast>
}