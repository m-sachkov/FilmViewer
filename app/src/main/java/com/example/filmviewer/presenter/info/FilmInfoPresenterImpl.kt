package com.example.filmviewer.presenter.info

import com.example.filmviewer.utils.*
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.filmviewer.R
import com.example.filmviewer.view.info.FilmInfoFragment

class FilmInfoPresenterImpl(bundle: Bundle?, fragment: FilmInfoFragment): FilmInfoPresenter {

    init {
        bundle?.let {
            fragment.apply {
                val localizedName = it.getString(BUNDLE_LOCALIZED_NAME)
                val originalName = it.getString(BUNDLE_NAME)

                if (!localizedName.equals(originalName)) {
                    setName(originalName)
                }
                setLocalizedName(localizedName)
                setYear(it.getString(BUNDLE_YEAR))
                setRating(it.getString(BUNDLE_RATING))
                setDescription(it.getString(BUNDLE_DESCRIPTION))
                it.getByteArray(BUNDLE_IMAGE)?.let {
                    var bitmap = fromByteArray(it)
                    if (bitmap == null) {
                        bitmap = loadResourceDrawable(R.drawable.placeholder)
                    }
                    setImage(bitmap)
                }
            }
        }
    }
}