package com.example.weathersearch.model

class Repository {
    private val api = API.getAPI()

    suspend fun getWeatherRead(cityName: String): UIState{
        val data = api.getWeatherRead(cityName)
        return generateUIState(data)
    }

    private fun generateUIState(data: WeatherResponse): UIState {
        return if (data != null)
            UIState.ResponseForecast(generatePresentationData(data))
        else
            UIState.Error("Network Error")
    }

    private fun generatePresentationData(data: WeatherResponse): List<PresentationDataForecast> {
        return data.list.map {
            PresentationDataForecast(
                it.weather[0].main,
                it.main.temp,
                PresentationDataDetails(
                    it.main.temp,
                    it.main.feels_like,
                    it.weather[0].main,
                    it.weather[0].description
                )
            )
        }
    }


}