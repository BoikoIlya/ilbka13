package com.example.weaterapptesttask.di

import android.content.Context
import com.example.weaterapptesttask.core.ManagerResource
import com.example.weaterapptesttask.core.WeatherCodeHandler
import com.example.weaterapptesttask.data.WeatherRepository
import com.example.weaterapptesttask.data.cloud.WeatherDto
import com.example.weaterapptesttask.data.cloud.WeatherService
import com.example.weaterapptesttask.domain.WeatherData
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HP on 23.03.2023.
 **/
@Module
@InstallIn(ActivityComponent::class)
class WeatherModule {

    companion object{
        private const val base_url: String = "https://api.open-meteo.com/"
    }

    @Provides
    @ActivityScoped
    fun provideWeatherService(): WeatherService{
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }


    @Provides
    @ActivityScoped
    fun provideManagerResource(@ApplicationContext context: Context): ManagerResource{
        return ManagerResource.Base(context)
    }
}

@Module
@InstallIn(ActivityComponent::class)
interface BindsWeatherModule{

    @Binds
    @ActivityScoped
    fun bindWeatherRepository(obj: WeatherRepository.Base): WeatherRepository

    @Binds
    @ActivityScoped
    fun bindToWeatherDataMapper(obj: WeatherDto.ToWeatherDataMapper): WeatherDto.Mapper<WeatherData>

    @Binds
    @ActivityScoped
    fun bindTWeatherCodeHandler(obj: WeatherCodeHandler.Base): WeatherCodeHandler

}