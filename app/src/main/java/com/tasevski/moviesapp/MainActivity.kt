package com.tasevski.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tasevski.moviesapp.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        Thread {
            viewModel.getMovies()
        }.start()

        viewModel.movies.observe(this@MainActivity) { result ->
            Log.d("TEST", result[1].title)
        }


    }
}