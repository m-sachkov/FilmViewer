package com.example.filmviewer.view.main

import android.os.Bundle

interface MainView {
    fun setAdapter(adapter: MainAdapter)
    fun executeOnUi(runnable: Runnable)
    fun openFilmInfoFragment(bundle: Bundle)
}