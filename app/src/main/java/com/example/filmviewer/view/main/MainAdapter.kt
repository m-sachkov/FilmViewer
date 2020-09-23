package com.example.filmviewer.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmviewer.R
import com.example.filmviewer.model.pojo.Film
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var data: List<Film>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmsViewHolder) {
            holder.bind(data[position])
        }
    }

    override fun getItemCount() = data.size

    fun setData(data: List<Film>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun itemChanged(position: Int) {
        notifyItemChanged(position)
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