package com.example.weathernaut.modelsForecast

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Int
)