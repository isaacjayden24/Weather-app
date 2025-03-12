package com.example.weathernaut.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathernaut.R
import com.example.weathernaut.modelsResponse.WeatherItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale





class ForecastAdapter(private val weatherList: List<WeatherItem>) :
    RecyclerView.Adapter<ForecastAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temperatureText: TextView = view.findViewById(R.id.Temperature_textview)
        val timeText: TextView = view.findViewById(R.id.Time_textView)
        val weatherIcon: ImageView = view.findViewById(R.id.forecastimageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = weatherList[position]


        //convert the temperature to celsius
        val celsiusTemperature = item.main.temp - 273.15
        holder.temperatureText.text = String.format("%.1f°C", celsiusTemperature)

        holder.timeText.text = formatTime(item.dt)

        val iconCode = item.weather.firstOrNull()?.icon ?: "01d"
        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}.png"

        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.weatherIcon)
    }

    override fun getItemCount(): Int = weatherList.size

    private fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000)) // Convert to milliseconds
    }
}
