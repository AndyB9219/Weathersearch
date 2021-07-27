package com.example.weathersearch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersearch.databinding.ForecastItemLayoutBinding
import com.example.weathersearch.model.PresentationDataForecast

class WeatherAdapter(private var dataSet: List<PresentationDataForecast>? = null,
                          private val callback: (Int) -> Unit): RecyclerView.Adapter<WeatherAdapter.CityForecastViewHolder>() {

    class CityForecastViewHolder(private val binding: ForecastItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun onBind(forecastItem: PresentationDataForecast, callback: (Int)-> Unit){
            binding.root.setOnClickListener { callback(adapterPosition) }

            binding.weatherCondition.text = forecastItem.main
            binding.weatherTemp.text = forecastItem.temp.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityForecastViewHolder(ForecastItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))

    override fun onBindViewHolder(holder: CityForecastViewHolder, position: Int) {
        dataSet?.let{
            holder.onBind(it[position], callback)
        }
    }

    override fun getItemCount() = dataSet?.size ?: 0

    fun updateDataSet(dataSet: List<PresentationDataForecast>?){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}