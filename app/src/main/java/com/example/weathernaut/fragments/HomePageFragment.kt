package com.example.weathernaut.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weathernaut.R
import com.example.weathernaut.WeatherApp
import com.example.weathernaut.models.CurrentWeather
import com.example.weathernaut.modelsForecast.Forecast
import com.example.weathernaut.ui.WeatherViewModel
import com.example.weathernaut.ui.WeatherViewModelFactory
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [HomePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePageFragment : Fragment() {

    private lateinit var airQualityTextView: View
    private lateinit var temperatureTextView:TextView
    private lateinit var weatherIconImageView: ImageView
    private lateinit var weatherDescriptionTextView: TextView

    private lateinit var windSpeedTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var feelsLikeTextView: TextView



    //declare viewmodel
    private val weatherViewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory((requireActivity().application as WeatherApp).weatherRepository)
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    //onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hide Actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        airQualityTextView=view.findViewById(R.id.airquality_textview)
        temperatureTextView=view.findViewById(R.id.temperature_textview)
        weatherIconImageView=view.findViewById(R.id.weathericon_imageview)
        weatherDescriptionTextView=view.findViewById(R.id.weatherdescription_textview)
        windSpeedTextView=view.findViewById(R.id.windspeed_textView)
        humidityTextView=view.findViewById(R.id.humidity_textview)
        feelsLikeTextView=view.findViewById(R.id.feelslike_textview)







        lifecycle.coroutineScope.launch {
           weatherViewModel.getCurrentWeather(10.0, 10.0)

        }

        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                updateUI(weather)
            }
        }










        airQualityTextView.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_airQualityFragment)
        }


    }





    //function to update UI for current weather
    private fun updateUI(currentWeather:CurrentWeather) {
        //convert the temperature to degree celsius
         val temperatureCelsius = currentWeather.main?.temp?.minus(273.15)
        //update the UI with the temperature
         temperatureTextView.text = "${temperatureCelsius?.toInt()}°C"



        //update the UI with the weather description
        weatherDescriptionTextView.text = currentWeather.weather?.get(0)?.description
        //update the UI with the weather icon
        //load icon url
        val iconUrl = "https://openweathermap.org/img/wn/${currentWeather.weather?.get(0)?.icon}@2x.png"
        Glide.with(requireContext()).load(iconUrl).into(weatherIconImageView)


        //update the UI with the wind speed
        windSpeedTextView.text = "Wind Speed: ${currentWeather.wind?.speed} m/s"


        //update the UI with the humidity
        humidityTextView.text = "Humidity: ${currentWeather.main?.humidity}%"

        //update the UI with the feels like temperature
        //convert the temperature to degree celsius
        val feelsLikeCelsius = currentWeather.main?.feels_like?.minus(273.15)
        //update the UI with the temperature
        feelsLikeTextView.text = "Feels Like: ${feelsLikeCelsius?.toInt()}°C"
    }


    //update the UI for forecast weather
    private fun updateForecastUI(forecast: Forecast) {
        //update the UI with the forecast data

    }








}