package com.example.skycast.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiHelper {
    val baseURL:String="https://api.openweathermap.org/data/2.5/"

    val weatherService by lazy {
        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            retrofit.create(WeatherService::class.java)
    }
}