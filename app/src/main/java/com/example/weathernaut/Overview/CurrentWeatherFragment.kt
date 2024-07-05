package com.example.weathernaut.Overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernaut.Adapter.MinMaxTemperatureAdapter
import com.example.weathernaut.Apicalls.WeatherApi
import com.example.weathernaut.Apicalls.WeatherDataT
import com.example.weathernaut.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentWeatherFragment : Fragment() {

    private lateinit var tempdisplay: TextView
    private lateinit var clouddisplay: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var feelDisplay: TextView

    private val city = "London"
    private val apiKey = "Add your api"
    private val lon = -74.0060
    private val lat = 40.7128

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_weather, container, false)
        tempdisplay = view.findViewById(R.id.tempdisplay)
        clouddisplay = view.findViewById(R.id.clouddisplay)
        recyclerView = view.findViewById(R.id.recyclerView)
        feelDisplay = view.findViewById(R.id.feelDisplay)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            fetchHourlyData()
            fetchCurrentWeather()
        }

        return view
    }

    private suspend fun fetchHourlyData() {
        try {
            val response = withContext(Dispatchers.IO) {
                WeatherApi.retrofitService.getHourlyData(lat, lon, apiKey)
            }

            if (isAdded) {
                //initial recycler view initialization was here
                val weatherList = response.list
                recyclerView.adapter = MinMaxTemperatureAdapter(weatherList)
            }

        } catch (t: Throwable) {
            if (isAdded) {
                Log.e("CurrentWeatherFragment", "Error fetching hourly data", t)
            }
        }
    }

    private suspend fun fetchCurrentWeather() {
        try {
            val response = withContext(Dispatchers.IO) {
                WeatherApi.retrofitService.getCurrentForecast(city, apiKey)
            }
            if (isAdded) {
                updateUI(response)
            }
        } catch (e: Exception) {
            if (isAdded) {
                Log.e("CurrentWeatherFragment", "Error fetching current weather data", e)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weatherData: WeatherDataT) {
        clouddisplay.text = "Clouds: ${weatherData.weather[0].description}"
        feelDisplay.text = "Feels like: ${weatherData.main.feels_like.toString()}°F"
        tempdisplay.text = "Temp: ${weatherData.main.temp.toInt()}°F"
    }
}


