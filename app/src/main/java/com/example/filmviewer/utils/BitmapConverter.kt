package com.example.filmviewer.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun toByteArray(bitmap: Bitmap?): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

fun fromByteArray(byteArray: ByteArray?): Bitmap?
        = byteArray?.let{ BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)}