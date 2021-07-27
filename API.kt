package com.example.weathersearch.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={api key}
// 65d00499677e59496ca2f318eb68c049

interface API {

    @GET("data/2.5/forecast")
    suspend fun getWeatherRead(@Query("q") cityname : String,
                       @Query("appid") auithId : String = "65d00499677e59496ca2f318eb68c049"): WeatherResponse
    companion object{
        fun getAPI():API{
            return Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API::class.java)
        }
    }
}