package com.example.skycast.model

data class Weather(
//	val alerts: List<String?>? = null,
	val current: Current? = null,
	val timezone: String? = null,
	val timezone_offset: Int? = null,
	val lon: Any? = null,
	val lat: Any? = null
)

data class WeatherItem(
	val icon: String? = null,
	val description: String? = null,
	val main: String? = null,
	val id: Int? = null
)

data class Current(
	val sunrise: Int? = null,
	val temp: Any? = null,
	val visibility: Int? = null,
	val uvi: Double? = null,
	val pressure: Int? = null,
	val clouds: Int? = null,
	val feels_like: Any? = null,
	val wind_gust: Any? = null,
	val dt: Int? = null,
	val wind_deg: Int? = null,
	val dew_point: Any? = null,
	val sunset: Int? = null,
	val weather: List<WeatherItem?>? = null,
	val humidity: Int? = null,
	val wind_speed: Any? = null
)

