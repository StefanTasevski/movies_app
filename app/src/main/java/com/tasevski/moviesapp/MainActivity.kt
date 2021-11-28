package com.tasevski.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                        return
                    }
                    if(!recyclerView.canScrollVertically(1)) {
                        viewModel.getMovies()
                    }
                }
            })

            viewModel.movies.observe(this@MainActivity) { result ->
                moviesAdapter.submitList(result)

                progressBar.isVisible = result.isNullOrEmpty()
                textViewError.isVisible = viewModel.error != null
                textViewError.text = viewModel.error
            }
        }
    }
}