package com.example.dicoding_restaurant_review_course

//class ApiConfig {
//    companion object {
//        fun getApiService() : ApiService{
//
//            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build()
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://restaurant-api.dicoding.dev/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//            return  retrofit.create(ApiService::class.java)
//        }
//    }
//}

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            // Tambahkan logging interceptor untuk melihat request/response
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Buat cache untuk meningkatkan performa
            val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
            val cache = Cache(context.cacheDir, cacheSize)

            // Konfigurasi OkHttpClient
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache) // Tambahkan cache
                .connectTimeout(30, TimeUnit.SECONDS) // Timeout koneksi
                .readTimeout(30, TimeUnit.SECONDS)    // Timeout membaca respons
                .writeTimeout(30, TimeUnit.SECONDS)   // Timeout menulis data
                .build()

            // Konfigurasi Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
