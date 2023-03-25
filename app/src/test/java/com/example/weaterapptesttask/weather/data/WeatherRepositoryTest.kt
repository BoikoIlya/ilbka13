package com.example.weaterapptesttask.weather.data

import com.example.weaterapptesttask.core.ManageResources
import com.example.weaterapptesttask.core.ObjectCreator
import com.example.weaterapptesttask.core.WeatherCodeHandler
import com.example.weaterapptesttask.weather.data.cache.CoordinatesTempCache
import com.example.weaterapptesttask.weather.data.cloud.WeatherDto
import com.example.weaterapptesttask.weather.data.cloud.WeatherService
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 25.03.2023.
 **/
class WeatherRepositoryTest: ObjectCreator() {

    lateinit var repository: WeatherRepository
    lateinit var service: TestWeatherService
    lateinit var managerResource: TestManageResources
    lateinit var cache: TestCache


    @Before
    fun setup(){
        service = TestWeatherService()
        managerResource = TestManageResources()
        cache = TestCache()
        repository = WeatherRepository.Base(
            service = service,
            mapper = WeatherDto.ToWeatherDataMapper(WeatherCodeHandler.Base(managerResource)),
            tempCoordinatesCache = cache
        )
    }

    @Test
    fun `test repository`() = runBlocking{
        managerResource.changeExpected("00")
       var actual =  repository.fetchData(12.0,12.0)

        assertEquals(getWeatherData(),actual)
        assertEquals(12.0, cache.data.latitude,0.0)
        assertEquals(12.0, cache.data.longitude,0.0)

        actual =  repository.fetchData(200.0,200.0)
        assertEquals(getWeatherData(),actual)
    }



    class TestWeatherService: WeatherService, ObjectCreator(){

        override suspend fun getWeatherByCoordinates(
            latitude: Double,
            longitude: Double,
            hourly: String,
            currentWeather: Boolean,
            forecast_days: Int
        ): WeatherDto = getWeatherDto()

    }

     class TestManageResources : ManageResources {

        private var value = ""

        fun changeExpected(string: String) {
            value = string
        }
        override fun getString(id: Int): String  = value
    }

    class TestCache: CoordinatesTempCache{
        var data = Point()

        override fun read(): Point = data

        override fun save(data: Point) {
          this.data = data
        }

    }
}