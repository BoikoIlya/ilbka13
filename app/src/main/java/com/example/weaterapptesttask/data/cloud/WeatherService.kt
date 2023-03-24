package com.example.weaterapptesttask.data.cloud

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by HP on 23.03.2023.
 **/
interface WeatherService {

    companion object{
        private const val path: String = "v1/forecast"
        private const val currentWeatherParam: Boolean = true
        private const val hourlyParam: String = "temperature_2m,windspeed_10m,winddirection_10m,weathercode"
    }

    @GET(path)
    suspend fun getWeatherByCoordinates(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = hourlyParam,
        @Query("current_weather") currentWeather: Boolean = currentWeatherParam
    ): WeatherDto
}