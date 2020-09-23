package com.example.filmviewer.model

import com.example.filmviewer.model.network.NetworkService
import com.example.filmviewer.model.pojo.FilmsArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Repository {

    private val networkService = NetworkService().getService()
    private var data: FilmsArray? = null

    fun getFilms(): FilmsArray? {
        if (data == null) {
            val deferred =
                GlobalScope.async {
                    networkService.getFilmsList()
                }
            data = runBlocking { deferred.await() }
        }
        return data
    }

    companion object {
        val intent = Repository()
    }
}