package com.example.weaterapptesttask.weather.domain

import com.example.weaterapptesttask.core.ObjectCreator
import com.example.weaterapptesttask.weather.data.WeatherRepository
import com.example.weaterapptesttask.weather.data.WeatherRepositoryTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

/**
 * Created by HP on 25.03.2023.
 **/
class WeatherInteractorTest: ObjectCreator() {

    lateinit var interactor: WeatherInteractor
    lateinit var repository: TestWeatherRepository
    lateinit var managerResource: WeatherRepositoryTest.TestManageResources

    @Before
    fun setup(){
        repository = TestWeatherRepository()
        managerResource = WeatherRepositoryTest.TestManageResources()
        interactor = WeatherInteractor.Base(
            repository = repository,
            handler = ResponseHandler.Base(managerResource)
        )
    }

    @Test
    fun `test interactor success`() = runBlocking {
        repository.setExpected(getWeatherData())
       val actual =  interactor.fetchData(1.0,1.0)

        assertEquals(WeatherResult.Success(getWeatherData()),actual)
    }

    @Test
    fun `test interactor error`() = runBlocking {
        repository.setError(UnknownHostException())
        val actual =  interactor.fetchData(1.0,1.0)

        assertEquals(WeatherResult.Failure::class.java,actual::class.java)
    }


    class TestWeatherRepository: WeatherRepository{
        private var data: WeatherData? =null
        private var error: java.lang.Exception?=null

        fun setError(e: Exception){
            error = e
        }

        fun setExpected(data: WeatherData){
            this.data = data
        }

        override suspend fun fetchData(latitude: Double, longitude: Double): WeatherData {
            if (error==null) return data!!
            else throw error!!
        }

    }
}