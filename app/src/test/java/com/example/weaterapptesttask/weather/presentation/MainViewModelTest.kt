package com.example.weaterapptesttask.weather.presentation

import androidx.lifecycle.LifecycleOwner
import com.example.weaterapptesttask.core.ObjectCreator
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherInteractor
import com.example.weaterapptesttask.weather.domain.WeatherResult
import com.yandex.mapkit.geometry.Point
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 25.03.2023.
 **/
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest: ObjectCreator() {

    lateinit var viewModel: MainViewModel
    lateinit var interactor: TestInteractor
    lateinit var communication: TestWeatherCommunication
    lateinit var placeMarkCommunication: TestPlaceMarkCommunication

    @Before
    fun setup(){
        interactor = TestInteractor()
        communication = TestWeatherCommunication()
        placeMarkCommunication = TestPlaceMarkCommunication()
    viewModel = MainViewModel(
        interactor = interactor,
        dispatchersList = TestDispatchersList(),
        communication =communication,
        mapper = WeatherResultMapper(communication),
        placeMarkCommunication = placeMarkCommunication
    )
    }

    @Test
    fun `test load data success`(){
        interactor.setExpected(WeatherResult.Success(getWeatherData()))
        viewModel.loadData(1.0,1.0)

        assertEquals(WeatherUiState.Loading,communication.stateList[0])
        assertEquals(WeatherUiState.Success,communication.stateList[1])

    }

    @Test
    fun `test load data error`(){
        viewModel.loadData(1.0,1.0)

        assertEquals(WeatherUiState.Loading,communication.stateList[0])
        assertEquals(WeatherUiState.Error("error"),communication.stateList[1])

    }

    @Test
    fun `test add Mark`(){
        viewModel.addMark(PlaceMarkState.SetMark(Point(1.0,1.0)))

        assertEquals(PlaceMarkState.SetMark::class.java, placeMarkCommunication.stateList[0]::class.java)

        viewModel.addMark(PlaceMarkState.Clear)

        assertEquals(PlaceMarkState.Clear, placeMarkCommunication.stateList[1])

    }



    class TestInteractor: WeatherInteractor{
        private var data: WeatherResult = WeatherResult.Failure("error")

        fun setExpected(data: WeatherResult){
            this.data = data
        }

        override suspend fun fetchData(latitude: Double, longitude: Double): WeatherResult = data

    }

    @ExperimentalCoroutinesApi
    private class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

    class TestWeatherCommunication: WeatherCommunication{
        val stateList = mutableListOf<WeatherUiState>()
        val weatherData = mutableListOf<WeatherData>()

        override fun showUiState(state: WeatherUiState) {
            stateList.add(state)
        }

        override fun showWeather(data: WeatherData) {
            weatherData.add(data)
        }

        override suspend fun collectState(
            owner: LifecycleOwner,
            collector: FlowCollector<WeatherUiState>,
        ) = Unit

        override suspend fun collectWeatherData(
            owner: LifecycleOwner,
            collector: FlowCollector<WeatherData>,
        ) = Unit

    }

    class TestPlaceMarkCommunication: PlaceMarkCommunication{
        val stateList = mutableListOf<PlaceMarkState>()

        override suspend fun collect(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<PlaceMarkState>,
        ) = Unit

        override fun map(newValue: PlaceMarkState) {
            stateList.add(newValue)
        }

    }
}