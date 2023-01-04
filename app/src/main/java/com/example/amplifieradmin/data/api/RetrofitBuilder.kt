package com.example.amplifieradmin.data.api

import com.amplifier.amplifier.data.network.RetrofitAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://amplifierapp.xyz/api_admin/"
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(add())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RetrofitAdapterFactory())
        .build()

    private fun add(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
        return client
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}