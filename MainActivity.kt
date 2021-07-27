package com.example.weathersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weathersearch.databinding.ActivityMainBinding
import com.example.weathersearch.view.CityForecast
import com.example.weathersearch.view.CityForecastDetail
import com.example.weathersearch.view.CitySearchFragment

class MainActivity : AppCompatActivity(),
    CitySearchFragment.ISearchCity,
    CityForecast.IDetailFragment {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateCitySearch()
    }

    private fun navigateCitySearch() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CitySearchFragment())
            .commit()
    }

    override fun searchData(cityName: String) {
        navigateCityForecast(cityName)
    }

    private fun navigateCityForecast(cityName: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CityForecast.newInstance(cityName))
            .commit()
    }

    override fun displayDetail(position: Int) {
        navigateCityDetails(position)
    }

    private fun navigateCityDetails(position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CityForecastDetail.newInstance(position))
            .addToBackStack(null)
            .commit()
    }
}