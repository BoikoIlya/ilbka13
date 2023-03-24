package com.example.weaterapptesttask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.weaterapptesttask.data.cloud.WeatherService
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 23.03.2023.
 **/
//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val service: WeatherService
//): ViewModel() {
//
//    init {
////        viewModelScope.launch {
////            Log.d("tag", "${service.getWeatherByCoordinates(53.901962,53.901962)}")
////        }
//    }
//}