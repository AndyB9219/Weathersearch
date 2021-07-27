package com.example.weathersearch.model

sealed class UIState{
    data class ResponseForecast(val data : List<PresentationDataForecast>) : UIState()
    data class Error(val errorMsg : String) : UIState()
    object Loading : UIState()

}
