package com.example.filmviewer.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmviewer.R
import com.example.filmviewer.presenter.main.MainPresenter
import com.example.filmviewer.presenter.main.MainPresenterImpl
import com.example.filmviewer.view.info.FilmInfoFragmentImpl

class MainViewImpl: Fragment(), MainView{

    private var presenter: MainPresenter = MainPresenterImpl(this)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)

        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(position) {
                    0,1,2 -> manager.spanCount
                    else -> 1
                }
            }
        }
        recyclerView.layoutManager = manager

        //presenter = MainPresenterImpl(this)
        presenter.onViewCreated()
    }

    override fun setAdapter(adapter: MainAdapter) {
        if (recyclerView.adapter == null) recyclerView.adapter = adapter
    }

    override fun executeOnUi(runnable: Runnable) {
        activity?.runOnUiThread { runnable.run() }
    }

    override fun openFilmInfoFragment(bundle: Bundle) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, FilmInfoFragmentImpl.create(bundle), FilmInfoFragmentImpl.toString())
            ?.addToBackStack(null)
            ?.commit()
    }

}