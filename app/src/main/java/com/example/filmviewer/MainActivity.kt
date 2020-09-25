package com.example.filmviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmviewer.view.main.MainViewImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, MainViewImpl())
            transaction.commit()
        }
    }
}