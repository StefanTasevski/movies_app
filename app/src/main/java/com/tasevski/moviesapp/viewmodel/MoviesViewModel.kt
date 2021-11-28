package com.tasevski.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasevski.moviesapp.api.MoviesApi
import com.tasevski.moviesapp.model.Movie
import java.net.URL

class MoviesViewModel : ViewModel() {
    val movies: MutableLiveData<List<Movie>> = MutableLiveData()

    val API_KEY = "c338cee6389edcf1bd4f342b751ceafd"
    val START_URL = "https://api.themoviedb.org/3/discover/movie"

    fun buildURL(): URL {
        return URL(START_URL+"?api_key="+API_KEY+"&page=1")
    }

    fun getMovies() {
        val moviesApi = MoviesApi()
        movies.postValue(moviesApi.fetchMovieData(buildURL()))
    }
}