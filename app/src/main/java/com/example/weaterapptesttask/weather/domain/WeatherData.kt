package com.example.weaterapptesttask.weather.domain

import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.example.weaterapptesttask.weather.presentation.MainViewModel
import com.example.weaterapptesttask.weather.presentation.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

/**
 * Created by HP on 23.03.2023.
 **/
data class WeatherData(
    private val coordinates: String,
    private val weatherList: List<WeatherItem>,
    private val currentTimeIndex: Int
){
    fun <T>map(mapper: Mapper<T>): T = mapper.map(coordinates, weatherList, currentTimeIndex)

    interface Mapper<T>{

        fun map(coordinates: String, weatherList: List<WeatherItem>, currentTimeIndex: Int): T
    }

    class ToUiMapper @Inject constructor(
        private val vpAdapter: ViewPagerAdapter,
        private val binding: ActivityMainBinding,
    ): Mapper<Unit> {
        override fun map(
            coordinates: String,
            weatherList: List<WeatherItem>,
            currentTimeIndex: Int
        ) {
            if(weatherList.isEmpty()) return
            vpAdapter.addCoordinates(coordinates)
            vpAdapter.addItems(weatherList)
            TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,pos->
                weatherList[pos].map(tab)
            }.attach()
            binding.tabLayout.getTabAt(if(currentTimeIndex==0) currentTimeIndex+1 else currentTimeIndex-1 )?.select()
            binding.tabLayout.getTabAt(currentTimeIndex)?.select()
        }


    }
}
