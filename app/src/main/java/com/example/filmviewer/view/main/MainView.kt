package com.example.filmviewer.view.main

interface MainView {
    fun setAdapter(adapter: MainAdapter)
    fun executeOnUi(runnable: Runnable)
}