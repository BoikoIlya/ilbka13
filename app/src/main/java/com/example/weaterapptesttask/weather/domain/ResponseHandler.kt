package com.example.weaterapptesttask.weather.domain

import com.example.weaterapptesttask.R
import com.example.weaterapptesttask.core.ManagerResource
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by HP on 24.03.2023.
 **/
interface ResponseHandler {

    fun handle(e: Exception): String

    class Base @Inject constructor(
        private val managerResource: ManagerResource
    ): ResponseHandler {

        override fun handle(e: Exception):String = managerResource.getString(
            when(e){
                is UnknownHostException -> R.string.no_internet_message
                else -> R.string.service_unavailable_message
            }
        )

    }
}