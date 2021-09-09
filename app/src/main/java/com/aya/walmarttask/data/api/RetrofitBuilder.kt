package com.aya.walmarttask.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Api Builder
object RetrofitBuilder {

    const val BASE_URL = "https://mobile-tha-server.firebaseapp.com/"


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}