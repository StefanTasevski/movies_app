package com.tasevski.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasevski.moviesapp.api.MoviesApi
import com.tasevski.moviesapp.model.Movie
import java.net.URL

class MoviesViewModel : ViewModel() {
    val movies: MutableLiveData<ArrayList<Movie>?> = MutableLiveData()
    var error: String? = null

    val API_KEY = "c338cee6389edcf1bd4f342b751ceafd"
    val START_URL = "https://api.themoviedb.org/3/discover/movie"

    var pageNumber = 0

    fun buildURL(): URL {
        pageNumber++
        return URL(START_URL+"?api_key="+API_KEY+"&page="+pageNumber)
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