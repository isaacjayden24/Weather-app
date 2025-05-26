package com.example.weathernaut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernaut.R
import com.example.weathernaut.modelsPollution.Item8


class PollutionAdapter(private var pollutionList: List<Item8>) :
    RecyclerView.Adapter<PollutionAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val componentText: TextView = view.findViewById(R.id.component_textview)
        val concentrationText: TextView = view.findViewById(R.id.concentration_textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.airquality_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = pollutionList[position]


        holder.componentText.text = """
            CO: ${item.components.co}
            NO: ${item.components.no}
            NO2: ${item.components.no2}
            O3: ${item.components.o3}
            SO2: ${item.components.so2}
            PM2.5: ${item.components.pm2_5}
            PM10: ${item.components.pm10}
            NH3: ${item.components.nh3}
        """.trimIndent()


    }


    override fun getItemCount(): Int = pollutionList.size

    fun updateData(newList: List<Item8>) {
        pollutionList = newList
        notifyDataSetChanged()
    }
}
