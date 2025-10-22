package com.pandroid.vijayiwfhassignment.data.api

import okhttp3.OkHttpClient
import com.pandroid.vijayiwfhassignment.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun create(): ApiService {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}