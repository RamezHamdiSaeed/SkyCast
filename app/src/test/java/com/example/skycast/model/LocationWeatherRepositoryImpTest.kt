import com.example.skycast.model.City
import com.example.skycast.model.Current
import com.example.skycast.model.LocationWeatherRepositoryImp
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherForecast
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationWeatherRepositoryImpTest {

    private lateinit var repository: LocationWeatherRepositoryImp
    private lateinit var localDataSource: LocationsLocalDataSourceImpFake
    private lateinit var remoteDataSource: RemoteDataSourceFake

    lateinit var dummyWeatherInfoList: List<WeatherInfo>
    //weatherForcastData
    lateinit var dummyWeatherForecast: List<WeatherForecast>
    //currentWeatherConditions
    lateinit var dummyCurrentWeather: List<Weather>



    @Before
    fun setUp() {
        localDataSource = LocationsLocalDataSourceImpFake()
        remoteDataSource = RemoteDataSourceFake()

        repository = LocationWeatherRepositoryImp(localDataSource, RemoteDataSourceFake())
        //for data base
        dummyWeatherInfoList = listOf(WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description"))
        //for netWork
        dummyCurrentWeather= listOf(Weather(Current(),"time zone data",1,"123","123"))
        dummyWeatherForecast= listOf(WeatherForecast(City(),1))

    }

    @Test
    fun `test insert and retrieve location`() {
        // Given
        val weatherInfo = WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description")

        // When
        val insertedId = runBlocking { repository.insertLocationDB(weatherInfo) }
        val result = runBlocking { repository.getAllLocationsDB().toList() }

        // Then
        assertEquals(1L, insertedId)
    }

    @Test
    fun `test delete location`() {
        // Given
        val weatherInfo = WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description")
        runBlocking { repository.insertLocationDB(weatherInfo) }

        // When
        val deletedCount = runBlocking { repository.deleteLocationDB(weatherInfo) }
        val result = runBlocking { repository.getAllLocationsDB().toList() }

        // Then
        assertEquals(1, deletedCount)
    }
    @Test
    fun `test getCurrentLocationWeatherConditionsAPI`() {
        // Given
        val expectedStatus = Status.Success(dummyCurrentWeather)

        // When
        val result = runBlocking { repository.getCurrentLocationWeatherConditionsAPI("30.5853431", "31.5035127", "ar").toList() }

        // Then
        assertEquals(expectedStatus, result.first())
    }

    @Test
    fun `test getCurrentLocationWeatherForcastAPI`() {
        // Given
        val expectedStatus = Status.Success(dummyCurrentWeather)

        // When
        val result = runBlocking { repository.getCurrentLocationWeatherForcastAPI("30.5853431", "31.5035127").toList() }

        // Then
        assertEquals(expectedStatus, result.first())
    }

    @Test
    fun `test getLocationInfoByCoordinatesAPI`() {
        // Given
        val expectedStatus = Status.Success(dummyWeatherInfoList)

        // When
        val result = runBlocking { repository.getLocationInfoByCoordinatesAPI("30.5853431", "31.5035127").toList() }

        // Then
        assertEquals(expectedStatus, result.first())
    }

}
