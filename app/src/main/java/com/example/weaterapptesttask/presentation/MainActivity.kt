package com.example.weaterapptesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.weaterapptesttask.data.WeatherRepository
import com.example.weaterapptesttask.data.cloud.WeatherService
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.example.weaterapptesttask.domain.WeatherInteractor
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var interactor: WeatherInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        MapKitFactory.initialize(this);

        setContentView(binding.root)

        lifecycleScope.launch {
            Log.d("tag", "${interactor.fetchData(53.901962,53.901962)}")
        }

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 0
            this.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.map.map.move(
            CameraPosition(Point(53.901962, 27.558054), 11.0f,0.0f,0.0f),
            Animation(Animation.Type.SMOOTH,0f),
            null
        )
    }

    override fun onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.map.onStart();
        super.onStart()
    }

    override fun onStop() {
        binding.map.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}