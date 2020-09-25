package com.example.filmviewer.view.main

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.filmviewer.R
import com.example.filmviewer.model.pojo.Film
import kotlinx.android.synthetic.main.list_item.view.*
import org.w3c.dom.Text

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var films: List<Film>
    private lateinit var genres: Set<String>
    private var genreSelectionListener: GenreSelectionListener? = null
    private var filmSelectionListener: FilmSelectionListener? = null
    private var updateHeader = true

    enum class TYPE(val value: Int) {
        GENRES(0),
        GENRES_HEADER(1),
        FILMS_HEADER(2)
    }

    interface GenreSelectionListener {
        fun onGenreSelected(genre: String)
    }

    interface FilmSelectionListener {
        fun onFilmSelected(film: Film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE.GENRES_HEADER.value -> HeaderViewHolder.from(parent,"ЖАНРЫ")
            TYPE.GENRES.value -> GenresViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_genres, parent, false) as ViewGroup)
            TYPE.FILMS_HEADER.value -> HeaderViewHolder.from(parent,"ФИЛЬМЫ")
            else -> {
                val filmsHolder = FilmsViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.list_item, parent, false)
                )
                filmsHolder.itemView.setOnClickListener {
                    filmSelectionListener?.onFilmSelected(
                        films[filmsHolder.adapterPosition - TYPE.values().size]
                    )
                }
                return filmsHolder
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmsViewHolder -> holder.bind(films[position - TYPE.values().size])
            is GenresViewHolder -> if (updateHeader) holder.bind(genres)
        }
    }

    override fun getItemCount() = films.size + TYPE.values().size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE.GENRES_HEADER.value
            1 -> TYPE.GENRES.value
            2 -> TYPE.FILMS_HEADER.value
            else -> 3
        }
    }

    fun setFilmsList(films: List<Film>) {
        this.films = films
        updateHeader = false
        notifyDataSetChanged()
    }

    fun setFilmsGenres(genres: Set<String>) {
        this.genres = genres
        updateHeader = true
        notifyItemChanged(0)
    }

    fun subscribeOnGenreSelected(listener: GenreSelectionListener) {
        genreSelectionListener = listener
    }

    fun subscribeOnFilmSelected(listener: FilmSelectionListener) {
        filmSelectionListener = listener
    }

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from (parent: ViewGroup, header: String): HeaderViewHolder {
                val view =
                    LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_header, parent, false)
                if (view is TextView) {
                    view.text = header
                }
                return HeaderViewHolder(view)
            }
        }
    }

    inner class GenresViewHolder(val view: ViewGroup): RecyclerView.ViewHolder(view) {

        private var lastSelectedGenreView: CompoundButton? = null

        fun bind(genres: Set<String>) {
            val tableLayout = view as TableLayout
            tableLayout.removeAllViews()

            val columnsCount = 3
            var currentColumn = 1
            var tableRow = TableRow(tableLayout.context)
            for (genre in genres) {
                tableRow.addView(createButton(tableLayout.context, genre))
                if (currentColumn == columnsCount || genres.indexOf(genre) == genres.size - 1) {
                    currentColumn = 1
                    tableLayout.addView(tableRow)
                    tableRow = TableRow(tableLayout.context)
                }
                else {
                    currentColumn++
                }
            }
        }

        private fun createButton(context : Context, text: String): Button {
            val button = ToggleButton(context).apply {
                this.text = text
                textOff = text
                textOn = text
                textSize = 12f
                isAllCaps = false
                background = ContextCompat.getDrawable(context, R.drawable.toggle_btn_selector)
                setTextColor(ContextCompat.getColorStateList(context, R.color.btn_text_toggle))
            }
            button.setOnCheckedChangeListener { buttonView, _ ->
                if (lastSelectedGenreView == buttonView) {
                    lastSelectedGenreView = null
                    genreSelectionListener?.onGenreSelected("")
                }
                else {
                    lastSelectedGenreView?.isChecked = false
                    lastSelectedGenreView = buttonView
                    genreSelectionListener?.onGenreSelected(text)
                }
            }
            return button
        }
    }

    inner class FilmsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val nameView: TextView = view.list_film_name
        private val imageView: ImageView = view.list_film_image

        fun bind(film: Film) {
            nameView.text = film.localizedName
            if (film.image == null) {
                imageView.setImageResource(R.drawable.placeholder)
            }
            else {
                imageView.setImageBitmap(film.image)
            }
        }

    }
}