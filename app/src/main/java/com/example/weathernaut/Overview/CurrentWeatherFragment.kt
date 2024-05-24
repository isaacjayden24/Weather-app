package com.example.weathernaut.Overview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernaut.Adapter.MinMaxTemperatureAdapter
import com.example.weathernaut.Apicalls.WeatherApi
import com.example.weathernaut.Apicalls.WeatherDataT
import com.example.weathernaut.Apicalls.WeatherResponse
import com.example.weathernaut.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherFragment : Fragment() {

    private lateinit var tempdisplay: TextView
    private lateinit var clouddisplay: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var feelDisplay:TextView

    private val city = "London"
    private val apiKey = "9ced84a4ee2242da8f0fd1c2df253b53"
    private val lon = -74.0060
    private val lat = 40.7128


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_weather, container, false)
        tempdisplay = view.findViewById(R.id.tempdisplay)
        clouddisplay = view.findViewById(R.id.clouddisplay)
        recyclerView = view.findViewById(R.id.recyclerView)
        feelDisplay=view.findViewById(R.id.feelDisplay)

        fetchWeatherData()
        displayScreen()
        return view
    }
//hourly data to be displayed on the recycler view
    private fun fetchWeatherData() {
        val call = WeatherApi.retrofitService.getHourlyData(lat, lon, apiKey)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val weatherList = response.body()?.list ?: emptyList()
                        val layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.layoutManager = layoutManager

                        val adapter = MinMaxTemperatureAdapter(weatherList)
                        recyclerView.adapter = adapter
                    } else {
                        Log.e("CurrentWeatherFragment", "Failed to fetch data")
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                if (isAdded) {
                    Log.e("CurrentWeatherFragment", "Error fetching data", t)
                }
            }
        })
    }

    //current data to display on the  top screen

    private fun displayScreen(){
        val call=WeatherApi.retrofitService.getCurrentForecast(city, apiKey)
        call.enqueue(object : Callback<WeatherDataT> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<WeatherDataT>,
                response: Response<WeatherDataT>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val toBeDisplayed=response.body()?.weather?.get(0)?.description
                        clouddisplay.text= "clouds:${toBeDisplayed.toString()}"

                        val feelslike=response.body()?.main?.feels_like
                        feelDisplay.text="Feels like:${feelslike.toString()}"

                        val temptoBedisplayed=response.body()?.main?.temp
                        tempdisplay.text="Temperature: ${temptoBedisplayed.toString()}"

                    } else {
                        Log.e("CurrentWeatherFragment", "Failed to fetch data")
                    }
                }
            }

            override fun onFailure(call: Call<WeatherDataT>, t: Throwable) {
                if (isAdded) {
                    Log.e("CurrentWeatherFragment", "Error fetching data", t)
                }
            }
        })




    }
}

