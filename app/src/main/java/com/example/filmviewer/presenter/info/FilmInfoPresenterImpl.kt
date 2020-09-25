package com.example.filmviewer.presenter.info

import com.example.filmviewer.utils.*
import android.os.Bundle
import com.example.filmviewer.view.info.FilmInfoFragment

class FilmInfoPresenterImpl(bundle: Bundle?, fragment: FilmInfoFragment): FilmInfoPresenter {

    init {
        bundle?.let {
            fragment.apply {
                setLocalizedName(it.getString(BUNDLE_LOCALIZED_NAME))
                setName(it.getString(BUNDLE_NAME))
                setYear(it.getString(BUNDLE_YEAR))
                setRating(it.getString(BUNDLE_RATING))
                setDescription(it.getString(BUNDLE_DESCRIPTION))
                it.getByteArray(BUNDLE_IMAGE)?.let { setImage(fromByteArray(it)) }
            }
        }
    }
}