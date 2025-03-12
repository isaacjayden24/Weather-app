package com.example.weathernaut.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathernaut.models.CurrentWeather
import com.example.weathernaut.modelsPollution.Item8
import com.example.weathernaut.modelsResponse.WeatherItem
import com.example.weathernaut.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {

    //live data for current weather
    private val _weatherData = MutableLiveData<CurrentWeather>()
    val weatherData: LiveData<CurrentWeather> get() = _weatherData

    //live data for forecast weather
    private val _forecastData = MutableLiveData<List<WeatherItem>>()  // Extract 'list'
    val forecastData: LiveData<List<WeatherItem>> get() = _forecastData

    //live data for air pollution
    private val _airPollutionData = MutableLiveData<List<Item8>>()
    val airPollutionData: LiveData<List<Item8>> get() = _airPollutionData





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
    fun getForecastWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getForecast(latitude, longitude)
                _forecastData.postValue(response.list)  // Pass only 'list'
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching forecast weather: ${e.message}")
            }
        }
    }


    //function to fetch air pollution
    suspend fun getAirPollution(latitude: Double, longitude: Double){
        try {
            val response = repository.getAirPollution(latitude, longitude)
            _airPollutionData.postValue(response.list)
        }
        catch (e: Exception){
            Log.e("WeatherViewModel", "Error fetching air pollution: ${e.message}")
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
