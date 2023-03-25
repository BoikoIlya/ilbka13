package com.example.weaterapptesttask.weather.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherInteractor
import com.example.weaterapptesttask.weather.domain.WeatherResult
import com.google.android.material.tabs.TabLayout
import com.yandex.mapkit.map.PlacemarkMapObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 23.03.2023.
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val interactor: WeatherInteractor,
    private val communication: WeatherCommunication,
    private val mapper: WeatherResult.Mapper<Unit>,
    private val placeMarkCommunication: PlaceMarkCommunication,
): ViewModel(), CollectWeather {


    fun loadData(
        latitude: Double,
        longitude: Double
    ) = viewModelScope.launch(dispatchersList.io()) {
        communication.showUiState(WeatherUiState.Loading)
        val result = interactor.fetchData(latitude,longitude)
        result.map(mapper)

    }

    fun addMark(state: PlaceMarkState) = placeMarkCommunication.map(state)


    suspend fun collectPlaceMarkCommunication(
        owner: LifecycleOwner,
        collector: FlowCollector<PlaceMarkState>
    ) = placeMarkCommunication.collect(owner, collector)

    override suspend fun collectState(
        owner: LifecycleOwner,
        collector: FlowCollector<WeatherUiState>,
    ) = communication.collectState(owner, collector)

    override suspend fun collectWeatherData(
        owner: LifecycleOwner,
        collector: FlowCollector<WeatherData>,
    )  = communication.collectWeatherData(owner,collector)
}