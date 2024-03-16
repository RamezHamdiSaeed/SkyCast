package com.example.skycast.viewModel

import LocationWeatherRepositoryFake
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skycast.model.LocationWeatherRepository
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class MyViewModelTest{
    @get:Rule
    val rule= InstantTaskExecutorRule()
    lateinit var  viewModel:MyViewModel
    lateinit var repo:LocationWeatherRepository
    lateinit var dummyList:List<WeatherInfo>

    @Before
    fun setup(){
        repo=LocationWeatherRepositoryFake()

        viewModel= MyViewModel(repo)

        dummyList= listOf(WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description"))


    }
    @Test
    fun getWeatherConditions_getCurrentConditions(){
        viewModel?.getCurrentWeatherConditionsAPI()

        val value=viewModel?.currentWeatherConditions?.value

//        MatcherAssert.assertThat(value, Status.Loading)
        MatcherAssert.assertThat(value, `is`(Status.Loading))


    }
    @Test
    fun getCurrentWeatherForecast_getCurrentForecast() {
        viewModel.getCurrentWeatherForcastAPI(lat = "30.5853431", long = "31.5035127")

        val value = viewModel.currentWeatherForecast.value

        MatcherAssert.assertThat(value, `is`(Status.Loading))
    }
    @Test
    fun getLocationInfoByCoordinates_getLocationInfo() {
        viewModel.getLocationInfoByCoordinatesAPI(lat = "30.5853431", long = "31.5035127")

        val value = viewModel.locationInfoByCoordinates.value

        MatcherAssert.assertThat(value, `is`(Status.Loading))
    }

    @Test
    fun `test getLocationsDB`() {
        runBlocking {
            viewModel.getLocationsDB()
            viewModel.savedLocations.collect { status ->
                MatcherAssert.assertThat(status, `is`(Status.Loading))
            }
        }
    }

    @Test
    fun `test insertLocation`() {
        val location = WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description")
        runBlocking {
            viewModel.insertLocation(location)
            viewModel.savedLocations.collect { status ->
                MatcherAssert.assertThat(status, `is`(Status.Loading))
            }
        }
    }

    @Test
    fun `test deleteLocation`() {
        val location = WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description")
        runBlocking {
            viewModel.deleteLocation(location)
            viewModel.savedLocations.collect { status ->
                MatcherAssert.assertThat(status, `is`(Status.Loading))
            }
        }
    }


}