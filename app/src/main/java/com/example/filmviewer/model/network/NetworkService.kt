package com.example.filmviewer.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService() = retrofit.create(ServiceAPI::class.java)
}