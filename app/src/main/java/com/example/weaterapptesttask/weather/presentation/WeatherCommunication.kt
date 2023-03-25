package com.example.weaterapptesttask.weather.presentation


import androidx.lifecycle.LifecycleOwner
import com.example.weaterapptesttask.core.Communication
import com.example.weaterapptesttask.weather.domain.WeatherData
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Created by HP on 26.01.2023.
 **/
interface WeatherCommunication: CollectWeather {

    fun showUiState(state: WeatherUiState)

    fun showWeather(tracks: WeatherData)

    class Base @Inject constructor(
        private val weatherStateCommunication: WeatherStateCommunication,
        private val weatherDataCommunication: WeatherDataCommunication
    ): WeatherCommunication {

        override fun showUiState(state: WeatherUiState) = weatherStateCommunication.map(state)

        override fun showWeather(tracks: WeatherData) = weatherDataCommunication.map(tracks)


        override suspend fun collectState(
            owner: LifecycleOwner,
            collector: FlowCollector<WeatherUiState>,
        ) = weatherStateCommunication.collect(owner, collector)


        override suspend fun collectWeatherData(
            owner: LifecycleOwner,
            collector: FlowCollector<WeatherData>,
        ) = weatherDataCommunication.collect(owner,collector)

    }

}

interface WeatherStateCommunication: Communication.Mutable<WeatherUiState>{
    class Base @Inject constructor():
        Communication.UiUpdate<WeatherUiState>(WeatherUiState.BottomShitCollapsed),
        WeatherStateCommunication
}

interface WeatherDataCommunication: Communication.Mutable<WeatherData>{
    class Base @Inject constructor():
        Communication.UiUpdate<WeatherData>(WeatherData("", emptyList(),0)),
        WeatherDataCommunication
}

interface CollectWeather{

    suspend fun collectState(owner: LifecycleOwner, collector: FlowCollector<WeatherUiState>)

    suspend fun collectWeatherData(owner: LifecycleOwner, collector: FlowCollector<WeatherData>)
}
