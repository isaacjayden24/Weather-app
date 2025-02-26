package com.example.weathernaut.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.weathernaut.R



/**
 * A simple [Fragment] subclass.
 * Use the [AirQualityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AirQualityFragment : Fragment() {


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

    }

}