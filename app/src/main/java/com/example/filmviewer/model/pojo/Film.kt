package com.example.filmviewer.model.pojo

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Film (
    @SerializedName("id")
    var id: Int,
    @SerializedName("localized_name")
    var localizedName: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("year")
    var year: Int,
    @SerializedName("rating")
    var rating: Double,
    @SerializedName("image_url")
    var imageUrl: String?,
    var image: Bitmap?,
    @SerializedName("description")
    var description: String,
    @SerializedName("genres")
    var genres: List<String>
)