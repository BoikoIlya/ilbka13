package com.example.weaterapptesttask.weather.data.cloud

data class Hourly(
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>,
    val winddirection_10m: List<Int>,
    val windspeed_10m: List<Double>
)