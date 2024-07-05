package com.example.weathernaut.Apicalls
import retrofit2.Retrofit
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query


//private  const val BASE_URL="http://api.openweathermap.org/data/2.5/weather?london/9ced84a4ee2242da8f0fd1c2df253b53"
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


//9ced84a4ee2242da8f0fd1c2df253b53
//https://api.openweathermap.org/data/2.5/weather?q=London&appid=9ced84a4ee2242da8f0fd1c2df253b53



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit=Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
//declare the functions to call and post your api here

interface WeatherApiService{


    @GET("weather")
    suspend  fun getCurrentForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherDataT


   @GET("forecast")
 suspend fun getHourlyData(
       @Query("lat") latitude: Double,
       @Query("lon") longitude: Double,
       @Query("appid") apiKey: String
   ): WeatherResponse


}


object WeatherApi{
   val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}






