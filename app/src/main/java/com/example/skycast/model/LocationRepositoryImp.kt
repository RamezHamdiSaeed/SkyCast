package com.example.skycast.model

import android.util.Log
import com.example.skycast.network.RemoteDataSource
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class LocationRepositoryImp(val remoteDataSource: RemoteDataSourceImp) : LocationRepository {
    private val TAG ="LocationRepositoryImp"
    override suspend fun getCurrentLocationWeatherConditionsAPI(): Flow<Status> {
        return remoteDataSource.getCurrentWeatherConditions()
            .catch {
                e->
                Log.e(TAG, "getCurrentLocationWeatherConditionsAPI: Exception - ${e.message}")
                throw e        }
    }
}