package com.example.buscacep.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "https://viacep.com.br/"

    @Volatile private var apiService: ViaCepApiService? = null

    fun getInstance(): ViaCepApiService =
        apiService ?: synchronized(this) { apiService ?: buildApi().also { apiService = it } }

    private fun buildApi(): ViaCepApiService {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepApiService::class.java)
    }
}