package com.example.weathernaut.modelsPollution

data class Pollution(
    val coord: Coord,
    val list: List<Item8>
)

data class Coord(
    val lon: Double,
    val lat: Double
)