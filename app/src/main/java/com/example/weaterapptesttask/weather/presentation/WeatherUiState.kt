package com.example.weaterapptesttask.weather.presentation

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weaterapptesttask.databinding.ActivityMainBinding
import com.example.weaterapptesttask.databinding.WeatherFragmentBinding
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.google.android.material.bottomsheet.BottomSheetBehavior

sealed class WeatherUiState {

    abstract fun apply(
       binding: ActivityMainBinding,
    )

    object Success: WeatherUiState(){

        override fun apply(
            binding: ActivityMainBinding,
        )  {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            binding.progressBar.visibility = View.GONE
            binding.viewPager.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
            binding.error.root.visibility = View.GONE
        }

    }

    data class Error(
        private val message: String,
    ): WeatherUiState(){

        override fun apply(
            binding: ActivityMainBinding,
        ) {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            binding.progressBar.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.error.errorTv.text = message
            binding.error.root.visibility = View.VISIBLE
        }
    }

    object Loading: WeatherUiState(){

        override fun apply(
            binding: ActivityMainBinding,
        ) {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            binding.progressBar.visibility = View.VISIBLE
            binding.viewPager.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.error.root.visibility = View.GONE
        }

    }

    object BottomShitCollapsed: WeatherUiState(){

        override fun apply(
            binding: ActivityMainBinding,
        ) {
            BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
}
