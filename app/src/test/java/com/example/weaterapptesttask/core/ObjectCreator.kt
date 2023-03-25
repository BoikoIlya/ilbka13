package com.example.weaterapptesttask.core

import com.example.weaterapptesttask.weather.data.cloud.Hourly
import com.example.weaterapptesttask.weather.data.cloud.HourlyUnits
import com.example.weaterapptesttask.weather.data.cloud.WeatherDto
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherItem

/**
 * Created by HP on 25.03.2023.
 **/
abstract class ObjectCreator {

    fun getWeatherDto() = WeatherDto(
        elevation = 1.0, generationtime_ms = 1.0, hourly = Hourly(
            temperature_2m = listOf(1.0),
            time = listOf("1923-03-03T12:00"),
            weathercode = listOf(1),
            winddirection_10m = listOf(1),
            windspeed_10m = listOf(1.0)
        ), hourly_units = HourlyUnits(
            temperature_2m = "1",
            time = "",
            weathercode = "1",
            winddirection_10m = "1",
            windspeed_10m = "1"
        ), latitude = 1.0, longitude = 1.0, timezone = "1", timezone_abbreviation = "1", utc_offset_seconds = 0
    )

    fun getWeatherData() = WeatherData(
        coordinates = "1.0, 1.0",
        weatherList = listOf(WeatherItem("03.03\n12:00", "1.01","1.0 1","11","00")),
        currentTimeIndex = -1
    )
}