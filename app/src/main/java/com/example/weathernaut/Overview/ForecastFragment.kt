package com.example.weathernaut.Overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.weathernaut.Apicalls.WeatherApi
import com.example.weathernaut.Apicalls.WeatherDataT
import com.example.weathernaut.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastFragment : Fragment() {
    private lateinit var cityInput: EditText
    private lateinit var sunriseText: TextView
    private lateinit var sunsetText: TextView
    private lateinit var feelsLikeText: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windSpeedText: TextView
    private lateinit var maxTempText: TextView
    private lateinit var minTempText: TextView
    private lateinit var cloudCoverText: TextView
    private lateinit var pressureText: TextView
    private lateinit var searchBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        cityInput = view.findViewById(R.id.cityinput)
        sunriseText = view.findViewById(R.id.sunrsieText)
        sunsetText = view.findViewById(R.id.sunsetText)
        feelsLikeText = view.findViewById(R.id.feelslikeText)
        pressureText = view.findViewById(R.id.pressureText)
        humidityTextView = view.findViewById(R.id.humidityText)
        windSpeedText = view.findViewById(R.id.windspeedText)
        maxTempText = view.findViewById(R.id.maxtempText)
        minTempText = view.findViewById(R.id.mintempText)
        cloudCoverText = view.findViewById(R.id.cloudcoverText)
        searchBtn = view.findViewById(R.id.searchbtn)

        searchBtn.setOnClickListener {
            val city = cityInput.text.toString()
            val apiKey = "YOUR Api key"
            fetchData(city, apiKey)
        }

        return view
    }

    private fun fetchData(cityInput: String, apiKey: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    WeatherApi.retrofitService.getCurrentForecast(cityInput, apiKey)
                }
                if (isAdded) {
                    updateUI(response)
                }
            } catch (e: Exception) {
                if (isAdded) {
                    Log.e("NetworkFetch", "Error fetching data", e)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(weatherData: WeatherDataT) {
        sunriseText.text = weatherData.sys.sunrise.toString()
        sunsetText.text = weatherData.sys.sunset.toString()
        feelsLikeText.text = weatherData.main.feels_like.toString()
        pressureText.text = weatherData.main.pressure.toString()
        humidityTextView.text = weatherData.main.humidity.toString()
        windSpeedText.text = weatherData.wind.speed.toString()
        maxTempText.text = weatherData.main.temp_max.toInt().toString()
        minTempText.text = weatherData.main.temp_min.toString()
        cloudCoverText.text = weatherData.clouds.all.toString()
    }
}


