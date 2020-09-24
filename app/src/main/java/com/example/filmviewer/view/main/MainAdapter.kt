package com.example.filmviewer.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.filmviewer.R
import com.example.filmviewer.model.pojo.Film
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var films: List<Film>
    private lateinit var genres: Set<String>
    private lateinit var genreSelectedListener: GenreSelectedListener
    private var updateHeader = true
    private val TYPE_GENRES = 0
    private val TYPE_FILMS = 1

    interface GenreSelectedListener {
        fun onGenreSelected(genre: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_GENRES) {
            return GenresViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_genres, parent, false) as ViewGroup
            )
        }
        return FilmsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsViewHolder) {
            holder.bind(films[position - 1])
        }
        else if (holder is GenresViewHolder){
            if (updateHeader) {
                holder.bind(genres)
            }
        }
    }

    // list of films + header with genres
    override fun getItemCount() = films.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_GENRES
        }
        return TYPE_FILMS
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

    fun subscribeOnGenreSelected(listener: GenreSelectedListener) {
        genreSelectedListener = listener
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
                    genreSelectedListener.onGenreSelected("")
                }
                else {
                    lastSelectedGenreView?.isChecked = false
                    lastSelectedGenreView = buttonView
                    genreSelectedListener.onGenreSelected(text)
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