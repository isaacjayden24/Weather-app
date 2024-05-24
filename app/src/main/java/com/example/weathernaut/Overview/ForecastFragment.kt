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
import android.widget.TextSwitcher
import android.widget.TextView
import com.example.weathernaut.Apicalls.WeatherApi
import com.example.weathernaut.Apicalls.WeatherDataT
import com.example.weathernaut.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [ForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForecastFragment : Fragment() {
    private lateinit var cityinput:EditText
    private lateinit var sunriseText: TextView
    private lateinit var sunsetText: TextView
    private lateinit var feelslikeText: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windspeedText: TextView
    private lateinit var maxtempText: TextView
    private lateinit var mintempText: TextView
    private lateinit var cloudcoverText: TextView
    private lateinit var pressureText:TextView
    private lateinit var searchbtn:Button

   /* private val cityInput = "London"
    private val apiKey = "9ced84a4ee2242da8f0fd1c2df253b53"*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view=inflater.inflate(R.layout.fragment_forecast, container, false)
        cityinput=view.findViewById(R.id.cityinput)
        sunriseText=view.findViewById(R.id.sunrsieText)

        sunsetText=view.findViewById(R.id.sunsetText)
        feelslikeText=view.findViewById(R.id.feelslikeText)
        pressureText=view.findViewById(R.id.pressureText)

        humidityTextView=view.findViewById(R.id.humidityText)
        windspeedText=view.findViewById(R.id.windspeedText)

        maxtempText=view.findViewById(R.id.maxtempText)
        mintempText=view.findViewById(R.id.mintempText)
        cloudcoverText=view.findViewById(R.id.cloudcoverText)
        searchbtn=view.findViewById(R.id.searchbtn)

        searchbtn.setOnClickListener(){
            val apiProvided="9ced84a4ee2242da8f0fd1c2df253b53"
            val city=getInput()
            displayToViews(city,apiProvided)
        }
       /* displayToViews()*/






        return view
    }


    fun getInput():String{
        val cityName=cityinput.text.toString()
        return cityName
    }

    fun displayToViews(cityInput:String,apiKey:String){

        val call= WeatherApi.retrofitService.getCurrentForecast(cityInput, apiKey)
        call.enqueue(object : Callback<WeatherDataT> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<WeatherDataT>,
                response: Response<WeatherDataT>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val sunrisetoBeDisplayed=response.body()?.sys?.sunrise
                        sunriseText.text= sunrisetoBeDisplayed.toString()

                        val sunsettoBeDisplayed=response.body()?.sys?.sunset
                        sunsetText.text= sunsettoBeDisplayed.toString()

                        val feelsliketoBedisplayed=response.body()?.main?.feels_like
                        feelslikeText.text=feelsliketoBedisplayed.toString()

                        val pressuretoBedisplayed=response.body()?.main?.pressure
                        pressureText.text=pressuretoBedisplayed.toString()

                        val humiditytoBeDisplayed=response.body()?.main?.humidity
                        humidityTextView.text=humiditytoBeDisplayed.toString()

                        val windspeedtoBeDisplayed=response.body()?.wind?.speed
                        windspeedText.text=windspeedtoBeDisplayed.toString()

                        val maxtemptoBeDisplayed=response.body()?.main?.temp_max?.toInt()
                        maxtempText.text=maxtemptoBeDisplayed.toString()

                        val mintemptoBeDisplayed=response.body()?.main?.temp_min
                        mintempText.text=mintemptoBeDisplayed.toString()

                        val cloudcovertoBeDisplayed=response.body()?.clouds?.all
                        cloudcoverText.text=cloudcovertoBeDisplayed.toString()


                    } else {
                        Log.e("ForecastFragment", "Failed to fetch data")
                    }
                }
            }

            override fun onFailure(call: Call<WeatherDataT>, t: Throwable) {
                if (isAdded) {
                    Log.e("ForecastFragment", "Error fetching data", t)
                }
            }
        })




    }






}