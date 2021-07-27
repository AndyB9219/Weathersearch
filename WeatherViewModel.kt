package com.example.weathersearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathersearch.model.Repository
import com.example.weathersearch.model.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel: ViewModel() {
    private val repository = Repository()

    private val _weatherForecast: MutableLiveData<UIState> = MutableLiveData()
    val weatherForecast: LiveData<UIState> = _weatherForecast

    fun getWeatherByCityName(cityName: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getWeatherRead(cityName)
            withContext(Dispatchers.Main){
                _weatherForecast.value = response
            }
        }
    }
}