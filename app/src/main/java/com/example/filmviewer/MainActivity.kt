package com.example.filmviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmviewer.view.main.MainViewImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, MainViewImpl())
        transaction.commit()
    }
}