package com.example.filmviewer.view.info

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.filmviewer.R
import com.example.filmviewer.presenter.info.FilmInfoPresenter
import com.example.filmviewer.presenter.info.FilmInfoPresenterImpl
import kotlinx.android.synthetic.main.film_layout.view.*

class FilmInfoFragmentImpl: Fragment(), FilmInfoFragment {

    private lateinit var localizedNameView: TextView
    private lateinit var nameView: TextView
    private lateinit var yearView: TextView
    private lateinit var ratingView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var imageView: ImageView

    private val presenter: FilmInfoPresenter = FilmInfoPresenterImpl(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localizedNameView = view.inf_localized_name
        nameView = view.inf_name
        yearView = view.inf_year
        ratingView = view.inf_rating
        descriptionView = view.inf_description
        imageView = view.inf_image

        presenter.onArgsSet(args)
    }

    override fun setLocalizedName(value: String?) {
        localizedNameView.text = value
    }

    override fun setName(value: String?) {
        nameView.text = value
    }

    override fun setYear(value: String?) {
        yearView.text = value
    }

    override fun setRating(value: String?) {
        ratingView.text = value
    }

    override fun setDescription(value: String?) {
        descriptionView.text = value
    }

    override fun setImage(bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

    override fun loadResourceDrawable(id: Int)
            = ResourcesCompat.getDrawable(resources, R.drawable.placeholder, null)?.toBitmap()

    companion object {
        private var args: Bundle? = null

        fun create(bundle: Bundle): FilmInfoFragmentImpl {
            val fragment = FilmInfoFragmentImpl()
            args = bundle
            return fragment
        }
    }

}