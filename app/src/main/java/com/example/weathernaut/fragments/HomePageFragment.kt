package com.example.weathernaut.fragments

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathernaut.R
import com.example.weathernaut.WeatherApp
import com.example.weathernaut.adapter.ForecastAdapter
import com.example.weathernaut.models.CurrentWeather
import com.example.weathernaut.ui.WeatherViewModel
import com.example.weathernaut.ui.WeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import android.provider.Settings
import com.example.weathernaut.NetworkReceiver



class HomePageFragment : Fragment() {

    private lateinit var airQualityTextView: View
    private lateinit var temperatureTextView:TextView
    private lateinit var weatherIconImageView: ImageView
    private lateinit var weatherDescriptionTextView: TextView

    private lateinit var windSpeedTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var feelsLikeTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var forecastRecyclerView: RecyclerView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var networkReceiver: BroadcastReceiver? = null





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
        forecastRecyclerView=view.findViewById(R.id.forecast_recyclerView)
        cityTextView=view.findViewById(R.id.city_textview)
        timeTextView=view.findViewById(R.id.timetext_date)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        checkLocationPermissions()


        getCurrentDate()





        // Check internet connection
        if (!isInternetAvailable(requireContext())) {
            showInternetDialog()
        }else{
            checkLocationPermissions()
        }


















        //observe live data for current weather
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                updateUI(weather)
            }
        }





        //forecast weather
       forecastRecyclerView.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Observe LiveData for forecast weather
        weatherViewModel.forecastData.observe(viewLifecycleOwner) { weatherList ->
            forecastAdapter = ForecastAdapter(weatherList)
            forecastRecyclerView.adapter = forecastAdapter
        }

















        airQualityTextView.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_airQualityFragment)
        }


    }






    //function to get current weather

    fun getCurrentWeather(lat: Double, lon: Double) {


        lifecycle.coroutineScope.launch {
            weatherViewModel.getCurrentWeather(lat, lon)

        }
    }

    //function to get forecast weather
    private  fun getForecast(lat: Double,lon: Double){

        lifecycle.coroutineScope.launch {
            weatherViewModel.getForecastWeather(lat,lon)
        }

    }


    //function to get current date
    private fun getCurrentDate(){
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val formattedDate = dateFormat.format(currentDate)
        timeTextView.text = formattedDate
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




    //function for location


    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1001
            )
        } else {
            startLocationUpdates()
        }
    }



    // function to pass longitude and latitude to current weather and forecast weather functions

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude

                    //initialize functions that require lat and lon
                    getCurrentWeather(lat, lon)
                    getForecast(lat,lon)

                } else {
                    Toast.makeText(context, "Unable to retrieve location", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }







    override fun onResume() {
        super.onResume()
        // Register network receiver to listen for internet changes
        networkReceiver = NetworkReceiver {
             // Fetch data once internet is restored
            checkLocationPermissions()
        }
        requireContext().registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        // Unregister network receiver to prevent memory leaks
        if (networkReceiver != null) {
            requireContext().unregisterReceiver(networkReceiver)
        }
    }






    // Function to check internet connectivity
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    // Function to show an alert dialog if no internet
    private fun showInternetDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet Connection")
            .setMessage("Please enable Wi-Fi or Mobile Data to continue.")
            .setPositiveButton("Enable Internet") { _, _ ->
                // Open network settings
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }








}



