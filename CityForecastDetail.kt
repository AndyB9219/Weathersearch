package com.example.weathersearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weathersearch.R
import com.example.weathersearch.databinding.CityForecastDetailBinding
import com.example.weathersearch.model.PresentationDataDetails
import com.example.weathersearch.model.UIState
import com.example.weathersearch.viewmodel.WeatherViewModel

class CityForecastDetail: Fragment() {

    companion object{
        const val KEY_CITY_LIST_POSITION = "CityForecastDetails_KEY_CITY_LIST_POSITION"

        fun newInstance(position: Int) =
            CityForecastDetail().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CITY_LIST_POSITION, position)
                }
            }
    }

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }

    private lateinit var binding: CityForecastDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CityForecastDetailBinding.inflate(inflater)

        viewModel.weatherForecast.observe(viewLifecycleOwner){dataSet->
            arguments?.let { bundle->
                when(dataSet){
                    is UIState.ResponseForecast-> updateUI(dataSet.data[bundle.getInt(KEY_CITY_LIST_POSITION)].details)
                    is UIState.Error -> updateErrorView(dataSet.errorMsg)
                    UIState.Loading -> showLoading(true)
                }
            }
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun updateErrorView(errorMsg: String) {
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(weatherData: PresentationDataDetails) {
        showLoading(false)
        binding.tvFeelsLikeNumberDetail.text = requireContext().getString(R.string.tv_text_feels_like, weatherData.feelTemp.toString())
        binding.tvTempNumberDetail.text = weatherData.temp.toString()
        binding.tvWeatherStatusDetail.text = weatherData.mainWeatherType
        binding.tvWeatherStatusDescriptionDetail.text = weatherData.weatherdescription
    }

}