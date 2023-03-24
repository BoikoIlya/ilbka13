package com.example.weaterapptesttask.domain

/**
 * Created by HP on 23.03.2023.
 **/
data class WeatherData(
    private val coordinates: String,
    private val weatherList: List<WeatherItem>
)
