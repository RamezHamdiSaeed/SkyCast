package com.example.skycast.db

import android.util.Log
import com.example.skycast.model.WeatherInfo
import com.example.skycast.network.ApiHelper
import com.example.skycast.utility.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InvalidObjectException

class LocationsLocalDataSourceImp(private val locationsDao: LocationsDao) : LocationsLocalDataSource {
    private val TAG="LocationsLocalDataSourceImp"
    override suspend fun getAllLocations()  =flow<Status>{  try {
        val response = locationsDao.getAllLocations()
        emit(Status.Success(response))
        Log.d(TAG, "getAllLocations: ${response}")

    }
     catch (e: Exception) {
        emit(Status.Fail(InvalidObjectException("Exception: ${e.message}")))

    }
}.flowOn(Dispatchers.IO)

    override suspend fun insertLocation(weatherInfo: WeatherInfo): Long {
        return locationsDao.insertLocation(weatherInfo)
    }

    override suspend fun deleteLocation(weatherInfo: WeatherInfo): Int {
        return locationsDao.deleteLocation(weatherInfo)
    }
}