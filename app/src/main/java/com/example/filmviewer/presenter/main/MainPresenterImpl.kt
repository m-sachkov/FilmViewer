package com.example.filmviewer.presenter.main

import com.example.filmviewer.model.Repository
import com.example.filmviewer.model.pojo.Film
import com.example.filmviewer.view.main.MainAdapter
import com.example.filmviewer.view.main.MainView
import com.squareup.picasso.Picasso

class MainPresenterImpl(val mainView: MainView): MainPresenter {

    private val repository = Repository.intent
    private val adapter = MainAdapter()

    init {
        mainView.setAdapter(adapter)
        repository.getFilms()?.let {
            loadImagesFor(it.films)
            adapter.setData(it.films)
        }

    }

    private fun loadImagesFor(films: List<Film>) {
        Thread {
            films.forEach { film ->
                try {
                    film.image = Picasso.get().load(film.imageUrl).get()
                }
                catch (e: Exception) {}
                mainView.executeOnUi{
                    adapter.notifyItemChanged(films.indexOf(film))
                }
            }
        }.start()
    }
}