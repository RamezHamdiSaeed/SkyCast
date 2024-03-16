package com.example.skycast.network

import android.util.Log
import com.example.skycast.model.LocationInfo
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherForecast
import com.example.skycast.network.ApiHelper.weatherService
import com.example.skycast.utility.Status
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.InvalidObjectException
import java.net.HttpURLConnection
import java.net.URL

class RemoteDataSourceImp : RemoteDataSource {
    private val TAG: String = "RemoteDataSourceImp"


    override suspend fun getCurrentWeatherConditions(
        lat: String,
        long: String,
        language: String
    ) = flow<Status> {
        try {
            val response = ApiHelper.weatherService.getCurrentWeatherConditions(
                lat,
                long,
                language
            )
            if (response.isSuccessful) {
                val weather = response.body()
                if (weather != null) {
                    emit(Status.Success(weather))
                } else {
                    emit(Status.Fail(InvalidObjectException("Response body is null")))
                }
            } else {
                emit(Status.Fail(InvalidObjectException("Failed to retrieve weather conditions")))
                Log.d(TAG, "getCurrentWeatherConditions: asdfasdfasdfasdfasdfasdfsadf")

            }
        } catch (e: Exception) {
            emit(Status.Fail(InvalidObjectException("Exception: ${e.message}")))

        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCurrentWeatherForecast(lat: String, long: String)= flow {
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$long&units=metric&appid=8e3540a48d29fcaa9704ffd3b94bad07"

        try {
            val response = executeHttpGetRequest(url)
            val weatherForecast = Gson().fromJson(response, WeatherForecast::class.java)
            emit(Status.Success(weatherForecast))
        } catch (e: Exception) {
            emit(Status.Fail(e))
            Log.d(TAG, "getCurrentWeatherForecast: $e")
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getLocationInfoByCoordinates(lat: String, long: String) = flow <Status>{

        try {
            val response = ApiHelper.locationService.getLocationInfoByCoordinates(
                lat,
                long
            )
            if (response.isSuccessful) {
                val locationInfo = response.body()
                if (locationInfo != null) {
                    emit(Status.Success(locationInfo))
                } else {
                    emit(Status.Fail(InvalidObjectException("Response body is null")))
                }
            } else {
                emit(Status.Fail(InvalidObjectException("Failed to retrieve locationsInfo")))

            }

        } catch (e: Exception) {
            emit(Status.Fail(InvalidObjectException("Exception: ${e.message}")))

        }
    }.flowOn(Dispatchers.IO)
    private suspend fun executeHttpGetRequest(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                return response.toString()
            } else {
                throw Exception("HTTP request failed with code ${connection.responseCode}")
            }
        } finally {
            connection.disconnect()
        }
    }
}