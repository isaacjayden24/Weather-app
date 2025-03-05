package com.example.weathernaut.util

class Constants {

    companion object{
        const val API_KEY="e07c2f50fc49a23072c617d502f32625"
        const val BASE_URL="https://api.openweathermap.org"
        //const val BASE_URL="https://api.openweathermap.org/data/2.5/"
    }
}

/*2025-03-04 15:14:07.114 14013-14049 okhttp.OkHttpClient     com.example.weathernaut              I  {"cod":401, "message": "Invalid API key. Please see https://openweathermap.org/faq#error401 for more info."}
2025-03-04 15:14:07.114 14013-14049 okhttp.OkHttpClient     com.example.weathernaut              I  <-- END HTTP (108-byte body)
2025-03-04 15:14:07.117 14013-14013 System.err              com.example.weathernaut              W  retrofit2.HttpException: HTTP 401 Unauthorized
2025-03-04 15:14:07.117 14013-14013 System.err              com.example.weathernaut              W  	at retrofit2.KotlinExtensions$await$2$2.onResponse(KotlinExtensions.kt:53)
2025-03-04 15:14:07.117 14013-14013 System.err              com.example.weathernaut              W  	at retrofit2.OkHttpCall$1.onResponse(OkHttpCall.java:161)
2025-03-04 15:14:07.117 14013-14013 System.err              com.example.weathernaut              W  	at okhttp3.internal.connection.RealCall$AsyncCall.run(RealCall.kt:519)*/