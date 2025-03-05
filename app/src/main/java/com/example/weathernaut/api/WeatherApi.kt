package com.example.weathernaut.api

import com.example.weathernaut.models.CurrentWeather
import com.example.weathernaut.modelsForecast.Forecast
import com.example.weathernaut.modelsPollution.Pollution
import com.example.weathernaut.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    //for current weather
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String= API_KEY
    ): CurrentWeather

    //for forecast
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String=API_KEY
    ): Forecast

    //for air pollution
    @GET("data/2.5/air_pollution")
    suspend fun getAirPollution(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String=API_KEY
    ): Pollution
}