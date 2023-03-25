package com.example.weaterapptesttask.weather.presentation

import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherResult
import javax.inject.Inject

/**
 * Created by HP on 24.03.2023.
 **/
class WeatherResultMapper @Inject constructor(
    private val communication: WeatherCommunication,
):  WeatherResult.Mapper<Unit> {

    override  fun map(data: WeatherData, errorMessage: String) {
        if(errorMessage.isNotEmpty()) communication.showUiState(WeatherUiState.Error(errorMessage))
        else {
            communication.showWeather(data)
            communication.showUiState(WeatherUiState.Success)
        }

    }
}