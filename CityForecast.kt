package com.example.weathersearch.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathersearch.databinding.CityForecastFragmentBinding
import com.example.weathersearch.model.PresentationDataForecast
import com.example.weathersearch.model.UIState
import com.example.weathersearch.view.adapter.WeatherAdapter
import com.example.weathersearch.viewmodel.WeatherViewModel

class CityForecast: Fragment() {

    interface IDetailFragment{fun displayDetail(position: Int) }
    companion object{
        private val KEY_CITY_NAME: String = "CityForecast_KEY_CITY_NAME"

        fun newInstance(cityName: String) = CityForecast().apply {
            arguments = Bundle().apply {
                putString(KEY_CITY_NAME, cityName)
            }
        }
    }

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }

//    private val viewmodel: WeatherViewModel by activityViewModels{requireActivity()},{
//        viewmodelProvider.create(WeatherViewModel)
//    }

    private lateinit var binding: CityForecastFragmentBinding

    private val adapter: WeatherAdapter by lazy {
        WeatherAdapter(callback = ::openDetailFragmentCallback)
    }

    private lateinit var listener: IDetailFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is IDetailFragment -> listener = context
            else -> throw ExceptionInInitializerError("Incorrect Host Activity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = CityForecastFragmentBinding.inflate(inflater)
        setupUI()
        viewModel.weatherForecast.observe(viewLifecycleOwner){
            when(it){
                is UIState.ResponseForecast-> updateView(it.data)
                is UIState.Error -> updateErrorView(it.errorMsg)
                UIState.Loading -> showLoading(true)
            }
        }
        configureViewModel()
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

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun configureViewModel() {
        arguments?.let {
            it.getString(KEY_CITY_NAME)?.let { cityName->
                viewModel.getWeatherByCityName(cityName)
            }
        }
    }

    private fun updateView(data: List<PresentationDataForecast>?) {
        if (data != null)
            updateAdapter(data)
    }

    private fun updateAdapter(data: List<PresentationDataForecast>) {
        showLoading(false)
        adapter.updateDataSet(data)
    }

    private fun openDetailFragmentCallback(position: Int){
        listener.displayDetail(position)
    }
}