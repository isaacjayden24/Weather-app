package com.example.weathernaut.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathernaut.models.CurrentWeather
import com.example.weathernaut.modelsForecast.Forecast
import com.example.weathernaut.repository.WeatherRepository

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    //live data for current weather
    private val _weatherData = MutableLiveData<CurrentWeather>()
    val weatherData: LiveData<CurrentWeather> get() = _weatherData

    //live data for forecast weather
    private val _forecastData = MutableLiveData<Forecast>()
    val forecastData: LiveData<Forecast> get() = _forecastData



    //function to fetch current weather
    suspend fun getCurrentWeather(latitude: Double, longitude: Double) {
        try {
            val response = repository.getCurrentWeather(latitude, longitude)
            Log.d("WeatherViewModel", "Raw Response: $response")
            _weatherData.postValue(response)
        } catch (e: Exception) {

            //logcat
            Log.e("WeatherViewModel", "Error fetching current weather: ${e.message}")
            e.printStackTrace()
        }


    }

    //function to fetch forecast weather
    suspend fun getForecastWeather(latitude: Double, longitude: Double){

        try {
            val response = repository.getForecast(latitude, longitude)
            _forecastData.postValue(response)

        }catch (e:Exception) {

            //logcat
            Log.e("WeatherViewModel", "Error fetching forecast weather: ${e.message}")
            e.printStackTrace()


        }


    }











}

class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
