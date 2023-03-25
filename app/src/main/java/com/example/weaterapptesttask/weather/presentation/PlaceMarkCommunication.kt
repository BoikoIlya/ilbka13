package com.example.weaterapptesttask.weather.presentation

import com.example.weaterapptesttask.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 25.03.2023.
 **/

interface PlaceMarkCommunication: Communication.Mutable<PlaceMarkState>{
    class Base @Inject constructor():
        Communication.UiUpdate<PlaceMarkState>(PlaceMarkState.Clear),
        PlaceMarkCommunication
}