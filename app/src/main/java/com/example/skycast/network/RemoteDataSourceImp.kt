package com.example.skycast.network

import android.util.Log
import com.example.skycast.model.Weather
import com.example.skycast.utility.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.InvalidObjectException

class RemoteDataSourceImp : RemoteDataSource{
    private val TAG:String="RemoteDataSourceImp"
    override suspend fun getCurrentWeatherConditions() = flow<Status> {
        try {
            val response = ApiHelper.weatherService.getCurrentWeatherConditions()
            if (response.isSuccessful) {
                val weather = response.body()
                if (weather != null) {
                    emit(Status.Success(weather))
                } else {
                    emit(Status.Fail(InvalidObjectException("Response body is null")))
                }
            } else {
                emit(Status.Fail(InvalidObjectException("Failed to retrieve weather conditions")))

            }
        } catch (e: Exception) {
            emit(Status.Fail(InvalidObjectException("Exception: ${e.message}")))

        }
    }.flowOn(Dispatchers.IO)
}