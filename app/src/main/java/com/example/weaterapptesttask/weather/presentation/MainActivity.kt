package com.example.weaterapptesttask.weather.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weaterapptesttask.R
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InputListener  {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var imageProvider: ImageProvider

    companion object{
        private const val minsk_latitude = 53.9
        private const val minsk_longitude = 27.56667
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        MapKitFactory.initialize(this);

        setContentView(binding.root)

        imageProvider = ImageProvider.fromResource(this, R.drawable.location_pin32)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 0
            this.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.map.map.addInputListener(this)

        binding.map.map.move(
            CameraPosition(Point(minsk_latitude, minsk_longitude), 11.0f,0.0f,0.0f),
            Animation(Animation.Type.SMOOTH,0f),
            null
        )
        val adapter = ViewPagerAdapter()
        binding.viewPager.adapter = adapter

        val mapper = WeatherData.ToUiMapper(adapter, binding)

        lifecycleScope.launch {
            viewModel.collectWeatherData(this@MainActivity){
                it.map(mapper)
            }
        }

        lifecycleScope.launch {
            viewModel.collectState(this@MainActivity){
                it.apply(binding)
            }
        }

        lifecycleScope.launch {
            viewModel.collectPlaceMarkCommunication(this@MainActivity){
                it.apply(binding,imageProvider)
            }
        }

        binding.error.retryBtn.setOnClickListener {
            viewModel.loadData(200.0,200.0)
        }
    }

    override fun onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.map.onStart()
    }


    override fun onStop() {
        binding.map.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    override fun onMapTap(p0: Map, point: Point) {
        viewModel.addMark(PlaceMarkState.SetMark(point))
        viewModel.loadData(point.latitude,point.longitude)
    }

    override fun onMapLongTap(p0: Map, p1: Point) = Unit
}