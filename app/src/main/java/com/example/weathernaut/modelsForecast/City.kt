package com.example.weathernaut.modelsForecast

data class City(
    val coord: Coord,
    val country: String,
    val id: Int?,
    val name: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)