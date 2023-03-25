package com.example.weaterapptesttask.weather.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weaterapptesttask.databinding.WeatherFragmentBinding
import com.example.weaterapptesttask.weather.domain.WeatherData
import com.example.weaterapptesttask.weather.domain.WeatherItem

/**
 * Created by HP on 04.02.2023.
 **/
class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerViewHolder>(){

    private val list =  mutableListOf<WeatherItem>()
    private var coordinates: String = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(WeatherFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.map(list[position], coordinates)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newItems: List<WeatherItem>){
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addCoordinates(coordinates:String){
        this.coordinates = coordinates
    }


}

class ViewPagerViewHolder(
    private val binding: WeatherFragmentBinding,
): ViewHolder(binding.root){

    private val mapper = WeatherItem.FillInfoLayout(binding)

    fun map(data: WeatherItem, coordinates: String){
        data.map(mapper)
        binding.coordinates.text = coordinates
    }

}