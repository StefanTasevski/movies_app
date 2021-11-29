package com.tasevski.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasevski.moviesapp.BuildConfig
import com.tasevski.moviesapp.api.MoviesApi
import com.tasevski.moviesapp.model.Movie
import java.net.URL

class MoviesViewModel : ViewModel() {
    val movies: MutableLiveData<ArrayList<Movie>?> = MutableLiveData()
    var error: String? = null

    private val apiKey = BuildConfig.API_KEY
    private val url = "https://api.themoviedb.org/3/discover/movie"
    private var pageNumber = 0

    private fun buildURL(): URL {
        pageNumber++
        return URL("$url?api_key=$apiKey&page=$pageNumber")
    }

    fun getMovies() {
        val moviesApi = MoviesApi()
        Thread {
            error = moviesApi.error
            if(movies.value?.isNotEmpty() == true) {
                val list = movies.value
                list?.addAll(moviesApi.fetchMovieData(buildURL())?.toMutableList() ?: arrayListOf())
                movies.postValue(list)
            }
            else {
                movies.postValue(moviesApi.fetchMovieData(buildURL()))
            }
        }.start()
    }
}