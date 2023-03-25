package com.example.weaterapptesttask.weather.presentation

import com.example.weaterapptesttask.R
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

/**
 * Created by HP on 25.03.2023.
 **/
sealed interface PlaceMarkState{

    fun apply(binding: ActivityMainBinding, imageProvider: ImageProvider)

    object Clear: PlaceMarkState {
        override fun apply(binding: ActivityMainBinding,imageProvider: ImageProvider){
            binding.map.map.mapObjects.clear()
        }
    }

    data class SetMark(
        private val point: Point,
    ): PlaceMarkState {

        override fun apply(binding: ActivityMainBinding,imageProvider: ImageProvider) {
            binding.map.map.mapObjects.clear()
            binding.map.map.mapObjects.addPlacemark(point,imageProvider)
        }

    }

}