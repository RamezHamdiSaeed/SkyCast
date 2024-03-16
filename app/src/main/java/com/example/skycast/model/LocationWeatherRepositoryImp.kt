package com.example.skycast.model

import android.util.Log
import com.example.skycast.db.LocationsLocalDataSource
import com.example.skycast.db.LocationsLocalDataSourceImp
import com.example.skycast.network.RemoteDataSource
import com.example.skycast.network.RemoteDataSourceImp
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LocationWeatherRepositoryImp(val localDataSource: LocationsLocalDataSource, val remoteDataSource: RemoteDataSource) : LocationWeatherRepository {
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

    override suspend fun getCurrentLocationWeatherForcastAPI(
        lat: String,
        long: String
    ): Flow<Status> = remoteDataSource.getCurrentWeatherForecast(lat, long)

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

    override suspend fun getAllLocationsDB() : Flow<Status>{
        return localDataSource.getAllLocations()
    }

    override suspend fun insertLocationDB(location: WeatherInfo): Long {
        return localDataSource.insertLocation(location)
    }

    override suspend fun deleteLocationDB(location: WeatherInfo): Int {
        return localDataSource.deleteLocation(location)
    }
}