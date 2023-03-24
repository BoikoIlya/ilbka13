package com.example.weaterapptesttask.data

import com.example.weaterapptesttask.core.Mapper
import com.example.weaterapptesttask.data.cloud.WeatherDto
import com.example.weaterapptesttask.data.cloud.WeatherService
import com.example.weaterapptesttask.domain.WeatherData
import javax.inject.Inject

/**
 * Created by HP on 23.03.2023.
 **/
interface WeatherRepository {

    suspend fun fetchData(
        latitude: Double,
        longitude: Double
    ): WeatherData

    class Base @Inject constructor(
        private val service: WeatherService,
        private val mapper: WeatherDto.Mapper<WeatherData>
    ): WeatherRepository{

        override suspend fun fetchData(
            latitude: Double,
            longitude: Double
        ): WeatherData = service.getWeatherByCoordinates(latitude, longitude).map(mapper)

    }
}