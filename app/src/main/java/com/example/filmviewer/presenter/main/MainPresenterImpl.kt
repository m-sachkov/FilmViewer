package com.example.filmviewer.presenter.main

import android.os.Bundle
import com.example.filmviewer.model.Repository
import com.example.filmviewer.model.pojo.Film
import com.example.filmviewer.utils.*
import com.example.filmviewer.view.main.MainAdapter
import com.example.filmviewer.view.main.MainView
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashSet

class MainPresenterImpl(val mainView: MainView): MainPresenter,
    MainAdapter.GenreSelectionListener, MainAdapter.FilmSelectionListener{

    private val repository = Repository.intent
    private val adapter = MainAdapter()

    init {
        adapter.subscribeOnGenreSelected(this)
        adapter.subscribeOnFilmSelected(this)
    }

    override fun onViewCreated() {
        mainView.setAdapter(adapter)
        repository.getFilms()?.let {
            val sortedList =
                sortByLocalizedName(sortByGenre(it.films, MainAdapter.lastSelectedGenre))
            loadImagesFor(sortedList)
            adapter.setFilmsList(sortedList)
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
                if (film.image == null) {
                    try {
                        film.image = Picasso.get().load(film.imageUrl).get()
                        mainView.executeOnUi {
                            adapter.refreshElementAt(films.indexOf(film))
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }.start()
    }

    private fun sortByGenre(films: List<Film>, genre: String): List<Film> {
        if (genre.isEmpty()) {
            return films
        }
        val result = LinkedList<Film>()
        films.forEach {
            if (it.genres.contains(genre)) {
                result.add(it)
            }
        }
        return result
    }

    override fun onGenreSelected(genre: String) {
        repository.getFilms()?.films?.let { films->
            if (genre.isEmpty()) {
                adapter.setFilmsList(sortByLocalizedName(films))
            }
            else {
                val incompleteList = sortByGenre(films, genre)
                adapter.setFilmsList(
                    sortByLocalizedName(incompleteList))
            }
        }
    }

    private fun sortByLocalizedName(data: List<Film>) = data.sortedBy { it.localizedName }

    override fun onFilmSelected(film: Film) {
        val bundle = Bundle().apply {
            putString(BUNDLE_LOCALIZED_NAME, film.localizedName)
            putString(BUNDLE_NAME, film.name)
            putString(BUNDLE_YEAR, film.year.toString())
            putString(BUNDLE_RATING, film.rating.toString())
            putString(BUNDLE_DESCRIPTION, film.description)
            putByteArray(BUNDLE_IMAGE, toByteArray(film.image))
        }
        mainView.openFilmInfoFragment(bundle)
    }
}