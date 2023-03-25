package com.example.weaterapptesttask.weather.domain

/**
 * @author Asatryan on 18.09.2022
 */
sealed class WeatherResult {

    interface Mapper<T> {
         fun map(data: WeatherData, errorMessage: String): T
    }

    abstract  fun <T> map(mapper: Mapper<T>): T

    data class Success(private val data: WeatherData) : WeatherResult() {
        override  fun <T> map(mapper: Mapper<T>): T = mapper.map(data, "")
    }

    data class Failure(private val message: String) : WeatherResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(WeatherData("", emptyList(),0), message)
    }
}