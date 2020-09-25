package com.example.filmviewer.presenter.info

import android.os.Bundle
import com.example.filmviewer.R
import com.example.filmviewer.utils.*
import com.example.filmviewer.view.info.FilmInfoFragment

class FilmInfoPresenterImpl(val fragment: FilmInfoFragment): FilmInfoPresenter {

    override fun onArgsSet(bundle: Bundle?) {
        if (bundle == null) return
        fragment.apply {
            val localizedName = bundle.getString(BUNDLE_LOCALIZED_NAME)
            val originalName = bundle.getString(BUNDLE_NAME)

            if (!localizedName.equals(originalName)) {
                setName(originalName)
            }
            setLocalizedName(localizedName)
            setYear(bundle.getString(BUNDLE_YEAR))
            setRating(bundle.getString(BUNDLE_RATING))
            setDescription(bundle.getString(BUNDLE_DESCRIPTION))
            bundle.getByteArray(BUNDLE_IMAGE)?.let {
                var bitmap = fromByteArray(it)
                if (bitmap == null) {
                    bitmap = loadResourceDrawable(R.drawable.placeholder)
                }
                setImage(bitmap)
            }
        }
    }
}