package com.example.skycast.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skycast.model.WeatherInfo

@Database(entities = arrayOf(WeatherInfo::class), version = 1 )
abstract class LocationsDB : RoomDatabase() {
    abstract fun getProductsDao(): LocationsDao
    companion object{
        @Volatile
        private var INSTANCE: LocationsDB? = null
        fun getInstance (ctx: Context): LocationsDB{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, LocationsDB::class.java, "locations_database")
                    .build()
                INSTANCE = instance
                instance }
        }
    }
}
