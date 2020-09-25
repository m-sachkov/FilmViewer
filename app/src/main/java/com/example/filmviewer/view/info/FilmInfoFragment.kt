package com.example.filmviewer.view.info

import android.graphics.Bitmap

interface FilmInfoFragment {
    fun setLocalizedName(value: String?)
    fun setName(value: String?)
    fun setYear(value: String?)
    fun setRating(value: String?)
    fun setDescription(value: String?)
    fun setImage(bitmap: Bitmap?)
    fun loadResourceDrawable(id: Int): Bitmap?
}