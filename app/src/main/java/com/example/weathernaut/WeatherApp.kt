package com.example.weathernaut

import android.app.Application
import com.example.weathernaut.api.RetrofitInstance
import com.example.weathernaut.repository.WeatherRepository

class WeatherApp : Application() {


    val api by lazy { RetrofitInstance.api }
    val weatherRepository by lazy { WeatherRepository(api) }

}