package com.example.weaterapptesttask.weather.data.cloud

import android.annotation.SuppressLint
import com.example.weaterapptesttask.core.WeatherCodeHandler
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherItem
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

data class WeatherDto(
    private val elevation: Double,
    private val generationtime_ms: Double,
    private val hourly: Hourly,
    private val hourly_units: HourlyUnits,
    private val latitude: Double,
    private val longitude: Double,
    private val timezone: String,
    private val timezone_abbreviation: String,
    private val utc_offset_seconds: Int,
){

    fun <T>map(mapper: Mapper<T>): T = mapper.map(
        elevation,
        generationtime_ms,
        hourly,
        hourly_units,
        latitude,
        longitude,
        timezone,
        timezone_abbreviation,
        utc_offset_seconds
    )

    interface Mapper<T>{

        fun map(
            elevation: Double,
            generationtime_ms: Double,
            hourly: Hourly,
            hourly_units: HourlyUnits,
            latitude: Double,
            longitude: Double,
            timezone: String,
            timezone_abbreviation: String,
            utc_offset_seconds: Int,
        ): T
    }

    class ToWeatherDataMapper @Inject constructor(
        private val handler: WeatherCodeHandler,
    ): Mapper<WeatherData> {
        @SuppressLint("SimpleDateFormat")
        override fun map(
            elevation: Double,
            generationtime_ms: Double,
            hourly: Hourly,
            hourly_units: HourlyUnits,
            latitude: Double,
            longitude: Double,
            timezone: String,
            timezone_abbreviation: String,
            utc_offset_seconds: Int,
        ): WeatherData {
            val list = emptyList<WeatherItem>().toMutableList()
            var i = 0
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            val outputFormat = DateTimeFormatter.ofPattern("dd.MM\nHH:mm")

            hourly.time.forEach{time->

                val date = inputFormat.parse(time)

                list.add(
                    WeatherItem(
                    outputFormat.format(date),
                    hourly.temperature_2m[i].toString()+hourly_units.temperature_2m,
                    hourly.windspeed_10m[i].toString()+" "+hourly_units.windspeed_10m,
                    hourly.winddirection_10m[i].toString()+hourly_units.winddirection_10m,
                    handler.handle(hourly.weathercode[i])
                    )
                )
                i++
            }
            val index = hourly.time.indexOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")))
            return WeatherData("$latitude, $longitude", list, index)
        }

    }

}