package com.example.skycast.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skycast.model.WeatherInfo

@Database(entities = arrayOf(WeatherInfo::class), version = 2 )
abstract class LocationsDB : RoomDatabase() {
    abstract fun getProductsDao(): LocationsDao
    companion object{

        @Volatile
        private var INSTANCE: LocationsDB? = null
        fun getInstance (ctx: Context): LocationsDB{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, LocationsDB::class.java, "locations_database")
//                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance }
        }

    }
}
