package com.example.weathernaut.repository

import com.example.weathernaut.api.WeatherApi

class WeatherRepository(private val weatherApi: WeatherApi) {

    // Function to fetch current weather data
    suspend fun getCurrentWeather(latitude: Double, longitude: Double) =
        weatherApi.getCurrentWeather(latitude, longitude)



     // Function to fetch forecast data
    suspend fun getForecast(latitude: Double, longitude: Double) =
        weatherApi.getForecast(latitude, longitude)

    // Function to fetch air pollution data
    suspend fun getAirPollution(latitude: Double, longitude: Double) =
        weatherApi.getAirPollution(latitude, longitude)

}