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

class MainViewImpl: Fragment(), MainView{

    private lateinit var presenter: MainPresenter
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
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        presenter = MainPresenterImpl(this)
    }

    override fun setAdapter(adapter: MainAdapter) {
        recyclerView.adapter = adapter
    }

    override fun executeOnUi(runnable: Runnable) {
        activity?.runOnUiThread { runnable.run() }
    }

}