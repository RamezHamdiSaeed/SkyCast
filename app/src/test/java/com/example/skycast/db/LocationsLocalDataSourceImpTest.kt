package com.example.skycast.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocationsLocalDataSourceImpTest{
    private lateinit var locationsLocalDataSource: LocationsLocalDataSource
    private lateinit var locationsDao: LocationsDao
    private lateinit var database: LocationsDB

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, LocationsDB::class.java
        ).build()

        locationsDao = database.getProductsDao()
        locationsLocalDataSource = LocationsLocalDataSourceImp(locationsDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetLocations() = runBlocking {
        // Given a location
        val location = WeatherInfo("zagazig","","31.00","20","https://..","good weather")

        // When the location is inserted into the data source
        locationsLocalDataSource.insertLocation(location)

        // Then the location should be retrieved from the data source
        lateinit var locations:List<WeatherInfo>
        locationsLocalDataSource.getAllLocations().collect({
            locations=(it as Status.Success).data as List<WeatherInfo>
        })
        MatcherAssert.assertThat("1", CoreMatchers.`is` (locations.size.toString()))
        MatcherAssert.assertThat(location, CoreMatchers.`is` (locations[0]))
    }
    @Test
    fun deleteLocation_deleteUnfoundElement_0AsResultOfDeletion() = runBlocking {
        // Given a location
        val location = WeatherInfo("zagazig","","31.00","20","https://..","good weather")

        // When delete unExisted location
        val deletionResult=locationsLocalDataSource.deleteLocation(location)

        // Then the result must to be 0
        MatcherAssert.assertThat(deletionResult, `is` (0))
    }
    @Test
    fun insertLocation_insertAlreadyExistedElement_1AsResultOfInsertionBecauseOfReplace() = runBlocking {
        // Given a location
        val location = WeatherInfo("zagazig","","31.00","20","https://..","good weather")
        locationsLocalDataSource.insertLocation(location)
        // When delete unExisted location
        val insertionResult=locationsLocalDataSource.insertLocation(location)

        // Then the result must to be 1L
        MatcherAssert.assertThat(insertionResult, `is` (2L))
    }



}