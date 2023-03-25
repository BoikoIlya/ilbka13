package com.example.weaterapptesttask.weather.di

import android.content.Context
import android.widget.TableLayout
import com.example.weaterapptesttask.core.ManagerResource
import com.example.weaterapptesttask.core.WeatherCodeHandler
import com.example.weaterapptesttask.weather.data.WeatherRepository
import com.example.weaterapptesttask.weather.data.cache.CoordinatesTempCache
import com.example.weaterapptesttask.weather.data.cloud.WeatherDto
import com.example.weaterapptesttask.weather.data.cloud.WeatherService
import com.example.weaterapptesttask.weather.domain.ResponseHandler
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherInteractor
import com.example.weaterapptesttask.weather.domain.WeatherResult
import com.example.weaterapptesttask.weather.presentation.*
import com.google.android.material.tabs.TabLayout
import com.yandex.mapkit.geometry.Point
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HP on 23.03.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
class WeatherModule {

    companion object{
        private const val base_url: String = "https://api.open-meteo.com/"
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }


    @Provides
    @ViewModelScoped
    fun provideManagerResource(@ApplicationContext context: Context): ManagerResource{
        return ManagerResource.Base(context)
    }

    @Provides
    @ViewModelScoped
    fun providesCoordinatesTempCache(): CoordinatesTempCache{
        return CoordinatesTempCache.Base(Point(200.0,200.0))
    }


}

@Module
@InstallIn(ViewModelComponent::class)
interface BindsWeatherModule{

    @Binds
    @ViewModelScoped
    fun bindWeatherRepository(obj: WeatherRepository.Base): WeatherRepository

    @Binds
    @ViewModelScoped
    fun bindToWeatherDataMapper(obj: WeatherDto.ToWeatherDataMapper): WeatherDto.Mapper<WeatherData>

    @Binds
    @ViewModelScoped
    fun bindWeatherCodeHandler(obj: WeatherCodeHandler.Base): WeatherCodeHandler

    @Binds
    @ViewModelScoped
    fun bindResponseHandler(obj: ResponseHandler.Base): ResponseHandler

    @Binds
    @ViewModelScoped
    fun bindWeatherInteractor(obj: WeatherInteractor.Base): WeatherInteractor

    @Binds
    @ViewModelScoped
    fun bindDispatchersList(obj: DispatchersList.Base): DispatchersList

    @Binds
    @ViewModelScoped
    fun bindWeatherResultMapper(obj: WeatherResultMapper): WeatherResult.Mapper<Unit>

    @Binds
    @ViewModelScoped
    fun bindWeatherCommunication(obj: WeatherCommunication.Base): WeatherCommunication

    @Binds
    @ViewModelScoped
    fun bindWeatherDataCommunication(obj: WeatherDataCommunication.Base): WeatherDataCommunication

    @Binds
    @ViewModelScoped
    fun bindWeatherStateCommunication(obj: WeatherStateCommunication.Base): WeatherStateCommunication

    @Binds
    @ViewModelScoped
    fun bindPlaceMarkCommunication(obj: PlaceMarkCommunication.Base): PlaceMarkCommunication
}