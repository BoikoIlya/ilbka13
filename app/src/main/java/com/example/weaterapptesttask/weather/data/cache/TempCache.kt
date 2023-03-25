package com.example.weaterapptesttask.weather.data.cache

import com.yandex.mapkit.geometry.Point
import javax.inject.Inject

/**
 * Created by HP on 25.03.2023.
 **/
interface CoordinatesTempCache {

    fun read(): Point

    fun save(data: Point)

    class Base(
        private var data: Point
    ): CoordinatesTempCache{

        override fun read(): Point = data

        override fun save(data: Point) {
            this.data = data
        }


    }
}
