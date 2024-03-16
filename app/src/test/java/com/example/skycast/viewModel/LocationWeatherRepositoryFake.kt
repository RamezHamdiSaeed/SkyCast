import com.example.skycast.model.LocationWeatherRepository
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocationWeatherRepositoryFake : LocationWeatherRepository {

    override suspend fun getCurrentLocationWeatherConditionsAPI(
        lat: String,
        long: String,
        language: String
    ): Flow<Status> {
        // Mocked implementation to return a dummy loading status
        return flow { emit(Status.Loading) }
    }

    override suspend fun getCurrentLocationWeatherForcastAPI(
        lat: String,
        long: String
    ): Flow<Status> {
        // Mocked implementation to return a dummy loading status
        return flow { emit(Status.Loading) }
    }

    override suspend fun getLocationInfoByCoordinatesAPI(
        lat: String,
        long: String
    ): Flow<Status> {
        // Mocked implementation to return a dummy loading status
        return flow { emit(Status.Loading) }
    }

    override suspend fun getAllLocationsDB(): Flow<Status> {
        // Mocked implementation to return a dummy loading status
        return flow { emit(Status.Loading) }
    }

    override suspend fun insertLocationDB(location: WeatherInfo): Long {
        // Mocked implementation to return a dummy location ID
        return 1L
    }

    override suspend fun deleteLocationDB(location: WeatherInfo): Int {
        // Mocked implementation to return a dummy number of deleted rows
        return 1
    }
}
