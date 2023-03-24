package com.example.weaterapptesttask.domain

import com.example.weaterapptesttask.data.WeatherRepository
import javax.inject.Inject

/**
 * Created by HP on 24.03.2023.
 **/
interface WeatherInteractor {

    suspend fun fetchData(
        latitude: Double,
        longitude: Double
    ): WeatherResult

    class Base @Inject constructor(
        private val repository: WeatherRepository,
        private val handler: ResponseHandler,
    ): WeatherInteractor{

        override suspend fun fetchData(latitude: Double, longitude: Double): WeatherResult =
            try {
                val result = repository.fetchData(latitude, longitude)
                 WeatherResult.Success(result)
            }catch (e: Exception){
                 WeatherResult.Failure(handler.handle(e))
            }
    }

}