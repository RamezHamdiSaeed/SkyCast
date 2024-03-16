import com.example.skycast.db.LocationsLocalDataSource
import com.example.skycast.model.WeatherInfo
import com.example.skycast.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicLong

class LocationsLocalDataSourceImpFake : LocationsLocalDataSource {
    private val locationList = mutableListOf<WeatherInfo>()
    private val idCounter = AtomicLong(1L)

    override suspend fun getAllLocations(): Flow<Status> {
        return flow { emit(Status.Success(locationList)) }
    }

    override suspend fun insertLocation(weatherInfo: WeatherInfo): Long {
        val id = idCounter.getAndIncrement()
        weatherInfo.city = id.toString() // Assign a unique ID to the weather info
        locationList.add(weatherInfo)
        return id
    }

    override suspend fun deleteLocation(weatherInfo: WeatherInfo): Int {
        val removed = locationList.remove(weatherInfo)
        return if (removed) 1 else 0
    }
}
