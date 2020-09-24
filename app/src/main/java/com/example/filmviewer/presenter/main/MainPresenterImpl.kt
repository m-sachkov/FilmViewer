package com.example.filmviewer.presenter.main

import com.example.filmviewer.model.Repository
import com.example.filmviewer.model.pojo.Film
import com.example.filmviewer.view.main.MainAdapter
import com.example.filmviewer.view.main.MainView
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashSet

class MainPresenterImpl(val mainView: MainView): MainPresenter, MainAdapter.GenreSelectedListener {

    private val repository = Repository.intent
    private val adapter = MainAdapter()

    init {
        mainView.setAdapter(adapter)
        adapter.subscribeOnGenreSelected(this)
        repository.getFilms()?.let {
            loadImagesFor(it.films)
            adapter.setFilmsList(it.films)
            adapter.setFilmsGenres(getGenresSet(it.films))
        }
    }

    private fun getGenresSet(films: List<Film>): Set<String> {
        val result = HashSet<String>()
        for (film in films) {
            for (filmGenre in film.genres) {
                result.add(filmGenre)
            }
        }
        return result
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

    override fun onGenreSelected(genre: String) {
        if (genre.isEmpty()) {
            repository.getFilms()?.films?.let { adapter.setFilmsList(it) }
        }
        else {
            val sortedList = LinkedList<Film>()
            repository.getFilms()?.films.let { films ->
                films?.forEach {
                    if (it.genres.contains(genre)) {
                        sortedList.add(it)
                    }
                }
            }
            adapter.setFilmsList(sortedList)
        }
    }
}