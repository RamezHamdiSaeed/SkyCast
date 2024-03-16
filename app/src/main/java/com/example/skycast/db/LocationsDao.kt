package com.example.skycast.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skycast.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(weatherInfo: WeatherInfo):Long

    @Query("select * from locations")
     suspend fun getAllLocations(): List<WeatherInfo>

    @Delete
    suspend fun deleteLocation(weatherInfo: WeatherInfo):Int
}