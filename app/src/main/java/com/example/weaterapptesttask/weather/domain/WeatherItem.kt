package com.example.weaterapptesttask.weather.domain

import android.annotation.SuppressLint
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.example.weaterapptesttask.databinding.WeatherFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Calendar

data class WeatherItem(
    private val time: String,
    private val temperature: String,
    private val windSpeed: String,
    private val windDirection: String,
    private val weatherDescription: String
){

    fun <T>map(mapper: Mapper<T>): T = mapper.map(
        time,
        temperature,
        windSpeed,
        windDirection,
        weatherDescription
    )

    interface Mapper<T>{

        fun map(
            time: String,
            temperature: String,
            windSpeed: String,
            windDirection: String,
            weatherDescription: String
        ): T
    }

    fun map(tab: TabLayout.Tab){
        tab.text = time
    }

    class FillInfoLayout(
        private val binding: WeatherFragmentBinding,
    ): Mapper<Unit> {


        override fun map(
            time: String,
            temperature: String,
            windSpeed: String,
            windDirection: String,
            weatherDescription: String,
        ) = with(binding) {
            temperatureTv.text = temperature
            windSpeedTv.text = windSpeed
            windDirectionTv.text = windDirection
            descriptionTv.text = weatherDescription
        }


    }

}
