import com.example.skycast.model.Current
import com.example.skycast.model.Weather
import com.example.skycast.model.WeatherInfo
import com.example.skycast.network.RemoteDataSource
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceFake : RemoteDataSource {
    override suspend fun getCurrentWeatherConditions(
        lat: String,
        long: String,
        language: String
    ): Flow<Status> {
        // Simulate returning a successful status
        return flow { emit(Status.Success(listOf(Weather(Current(),"time zone data",1,"123","123")))) }
    }

    override suspend fun getCurrentWeatherForecast(lat: String, long: String): Flow<Status> {
        // Simulate returning a successful status
        return flow { emit(Status.Success(listOf(Weather(Current(),"time zone data",1,"123","123")))) }
    }

    override suspend fun getLocationInfoByCoordinates(lat: String, long: String): Flow<Status> {
        // Simulate returning a successful status
        return flow { emit(Status.Success(listOf(WeatherInfo("City", "30.0", "31.0", "Temp", "IconUrl", "Description")))) }
    }
}
