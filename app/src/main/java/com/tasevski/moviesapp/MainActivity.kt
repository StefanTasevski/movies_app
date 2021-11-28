package com.tasevski.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasevski.moviesapp.adapters.MoviesAdapter
import com.tasevski.moviesapp.databinding.ActivityMainBinding
import com.tasevski.moviesapp.viewmodel.MoviesViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        viewModel.getMovies()
        val moviesAdapter = MoviesAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = moviesAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            viewModel.movies.observe(this@MainActivity) { result ->
                moviesAdapter.submitList(result)

                progressBar.isVisible = result.isNullOrEmpty()
                textViewError.isVisible = result.isNullOrEmpty()
            }
        }
    }
}