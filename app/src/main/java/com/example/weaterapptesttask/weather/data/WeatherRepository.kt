package com.example.weaterapptesttask.weather.data

import com.example.weaterapptesttask.weather.data.cache.CoordinatesTempCache
import com.example.weaterapptesttask.weather.data.cloud.WeatherDto
import com.example.weaterapptesttask.weather.data.cloud.WeatherService
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.yandex.mapkit.geometry.Point
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
        private val mapper: WeatherDto.Mapper<WeatherData>,
        private val tempCoordinatesCache: CoordinatesTempCache
    ) : WeatherRepository {

        override suspend fun fetchData(
            latitude: Double,
            longitude: Double
        ): WeatherData {
            return if (latitude == 200.0 || longitude == 200.0) { //200, 200 coordinates don`t exist
                val coordinates = tempCoordinatesCache.read()
                service.getWeatherByCoordinates(coordinates.latitude, coordinates.longitude)
                    .map(mapper)
            } else {
                tempCoordinatesCache.save(Point(latitude,longitude))
                service.getWeatherByCoordinates(latitude, longitude).map(mapper)
            }

        }

    }
}