package com.example.weathernaut.fragments

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernaut.R
import com.example.weathernaut.WeatherApp
import com.example.weathernaut.adapter.ForecastAdapter
import com.example.weathernaut.adapter.PollutionAdapter
import com.example.weathernaut.ui.WeatherViewModel
import com.example.weathernaut.ui.WeatherViewModelFactory
import kotlinx.coroutines.launch
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [AirQualityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AirQualityFragment : Fragment() {

    private lateinit var cityInput: EditText
    private lateinit var airQualityRecyclerView: RecyclerView
    private lateinit var pollutionAdapter: PollutionAdapter
    private lateinit var searchBtn:ImageView



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
        return inflater.inflate(R.layout.fragment_airquality, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hide Actionbar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        cityInput=view.findViewById(R.id.cityInput)
        airQualityRecyclerView=view.findViewById(R.id.airquality_recyclerView)
        searchBtn=view.findViewById(R.id.search_button)









        // Initialize adapter with empty list
        pollutionAdapter = PollutionAdapter(emptyList())
        airQualityRecyclerView.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        airQualityRecyclerView.adapter = pollutionAdapter


        // Observe data and update adapter
        weatherViewModel.airPollutionData.observe(viewLifecycleOwner) { airPollutionList ->
            Log.d("RecyclerView", "Received ${airPollutionList.size} items")
            pollutionAdapter.updateData(airPollutionList)
        }




        searchBtn.setOnClickListener {
            val cityName = cityInput.text.toString()
            if (cityName.isNotBlank() ) {
                getLatLngFromCity(cityName)
            }else{

                Toast.makeText(requireContext(), "Please input city name", Toast.LENGTH_SHORT).show();


            }
        }










    }


    private fun getLatLngFromCity(cityName: String) {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocationName(cityName, 1)

            if (!addresses.isNullOrEmpty()) {
                val location = addresses[0]
                val latitude = location.latitude
                val longitude = location.longitude
                getAirPollution(latitude,longitude)
            } else {

                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show();


            }
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }


    //function to get air pollution
    private fun getAirPollution(lat:Double,lon:Double){
        lifecycle.coroutineScope.launch {
            weatherViewModel.getAirPollution(lat, lon)
        }
    }




}