package com.example.weaterapptesttask.data.cloud

data class HourlyUnits(
    val temperature_2m: String,
    val time: String,
    val weathercode: String,
    val winddirection_10m: String,
    val windspeed_10m: String
)