package com.example.weaterapptesttask.weather.di

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by HP on 23.03.2023.
 **/
@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("79c40b92-de66-40c0-a8da-7dcb6a446f03")
    }
}