package com.example.skycast.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiHelper {
    val weatherBaseUrl:String="https://api.openweathermap.org/data/2.5/"
    val locationBaseUrl:String="https://nominatim.openstreetmap.org/"

    val weatherService by lazy {
        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WeatherService::class.java)
    }
    val locationService by lazy {
        val retrofit=Retrofit.Builder()
            .baseUrl(locationBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(LocationService::class.java)
    }
}