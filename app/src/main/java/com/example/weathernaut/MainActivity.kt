package com.example.weathernaut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weathernaut.Overview.CurrentWeatherFragment
import com.example.weathernaut.Overview.ForecastFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set default fragment on app start
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, CurrentWeatherFragment())
                .commit()
        }

        // Set the listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.person -> selectedFragment = CurrentWeatherFragment()
                R.id.home -> selectedFragment = ForecastFragment()
                // R.id.settings -> selectedFragment = not implemented yet
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .commit()
            }
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.person
    }
}
