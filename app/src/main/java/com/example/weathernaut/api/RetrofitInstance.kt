package com.example.weathernaut.api

import com.example.weathernaut.util.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitInstance {
    //create retrofit instance

   /* companion object {
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        private const val BASE_URL= com.example.weathernaut.util.Constants.BASE_URL  //TODO :added this code here

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
        }

        val api: WeatherApi by lazy {
            retrofit.create(WeatherApi::class.java)
        }
    }*/

    companion object {
        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        private const val BASE_URL = com.example.weathernaut.util.Constants.BASE_URL

        //  Moshi instance with KotlinJsonAdapterFactory
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
        }

        val api: WeatherApi by lazy {
            retrofit.create(WeatherApi::class.java)
        }
    }


}