package com.example.weaterapptesttask

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        MapKitFactory.initialize(this);

        setContentView(binding.root)

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