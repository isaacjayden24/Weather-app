package com.example.weathernaut.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathernaut.Apicalls.WeatherItem
import com.example.weathernaut.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MinMaxTemperatureAdapter(private val weatherList: List<WeatherItem>) :
    RecyclerView.Adapter<MinMaxTemperatureAdapter.MinMaxTemperatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinMaxTemperatureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_min_max_temperature, parent, false)
        return MinMaxTemperatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: MinMaxTemperatureViewHolder, position: Int) {
        val weatherItem = weatherList[position]
        holder.bind(weatherItem)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class MinMaxTemperatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val minTemperatureTextView: TextView = itemView.findViewById(R.id.minTemperatureTextView)
        private val maxTemperatureTextView: TextView = itemView.findViewById(R.id.maxTemperatureTextView)
        private val weatherIconImageView:ImageView=itemView.findViewById(R.id.weatherIconImageView)
        private val dateView:TextView=itemView.findViewById(R.id.dateView)

        fun bind(weatherItem: WeatherItem) {
            minTemperatureTextView.text = "Min Temp: ${weatherItem.main.temp_min}°C"
            maxTemperatureTextView.text = "Max Temp: ${weatherItem.main.temp_max}°C"


            // Converting Unix timestamp to a human-readable format
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = Date(weatherItem.dt * 1000L)
            val formattedDate = sdf.format(date)

            dateView.text = "Time: $formattedDate"


            val weatherIconUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

            // Glide or Picasso to load the weather icon
            Glide.with(itemView.context)
                .load(weatherIconUrl)
                .into(weatherIconImageView)
        }
    }
}
