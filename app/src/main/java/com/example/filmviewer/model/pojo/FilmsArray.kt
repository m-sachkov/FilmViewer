package com.example.filmviewer.model.pojo

import com.google.gson.annotations.SerializedName

data class FilmsArray (
    @SerializedName("films")
    var films: List<Film>
)