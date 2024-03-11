package com.example.skycast.model

import android.util.Log
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class LocationWeatherRepositoryImp(val remoteDataSource: RemoteDataSourceImp) : LocationWeatherRepository {
    private val TAG ="LocationRepositoryImp"
    override suspend fun getCurrentLocationWeatherConditionsAPI(lat:String,
                                                                long: String ,
                                                                language:String): Flow<Status> {
        return remoteDataSource.getCurrentWeatherConditions(lat,
            long ,
            language)
            .catch {
                e->
                Log.e(TAG, "getCurrentLocationWeatherConditionsAPI: Exception - ${e.message}")
                throw e
            }
    }

    override suspend fun getLocationInfoByCoordinatesAPI(
        lat: String,
        long: String
    ): Flow<Status> {
        return remoteDataSource.getLocationInfoByCoordinates(lat, long)
            .catch { e ->
                Log.e(TAG, "getLocationInfoByCoordinatesAPI: Exception - ${e.message}")
                throw e
            }
    }
}