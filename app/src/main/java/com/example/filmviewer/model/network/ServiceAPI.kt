package com.example.filmviewer.model.network

import com.example.filmviewer.model.pojo.FilmsArray
import retrofit2.http.GET

interface ServiceAPI {
    @GET("films.json")
    suspend fun getFilmsList(): FilmsArray
}