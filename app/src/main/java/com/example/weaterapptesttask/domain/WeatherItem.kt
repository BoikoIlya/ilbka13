package com.example.weaterapptesttask.domain

data class WeatherItem(
    private val time: String,
    private val temperature: String,
    private val windSpeed: String,
    private val windDirection: String,
    private val weatherDescription: String
)
