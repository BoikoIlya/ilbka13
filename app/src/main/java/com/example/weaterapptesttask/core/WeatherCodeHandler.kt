package com.example.weaterapptesttask.core

import com.example.weaterapptesttask.R
import javax.inject.Inject

/**
 * Created by HP on 23.03.2023.
 **/
interface WeatherCodeHandler {

    fun handle(code: Int): String

    class Base @Inject constructor(
        private val managerResource: ManageResources
    ): WeatherCodeHandler{

        override fun handle(code: Int): String = managerResource.getString(
            when(code){
                0 -> R.string.clear_sky_message
                1,2,3 -> R.string.mainly_clear_message
                45,48 -> R.string.fog_message
                51,53,55 -> R.string.drizzle_message
                56,57 -> R.string.freezing_drizzle_message
                61,63,65 -> R.string.rain_message
                66,67 -> R.string.freezing_message
                71,73,75 -> R.string.snow_fall_message
                77 -> R.string.snow_message
                80,81,82 -> R.string.rain_showers_message
                85,86 -> R.string.snow_showers_message
                95 -> R.string.thunderstorm_moderate_message
                96,99 -> R.string.thunderstorm_hail_message
                else -> R.string.no_data
            }
        )
    }
}