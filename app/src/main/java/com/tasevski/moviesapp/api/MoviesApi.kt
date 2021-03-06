package com.tasevski.moviesapp.api

import android.text.TextUtils
import com.tasevski.moviesapp.model.Movie
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MoviesApi {

    var error: String? = null

    fun fetchMovieData(url: URL): ArrayList<Movie>? {
        var jsonResponse: String? = null
        try {
            jsonResponse = getResponseFromHttpUrl(url)
        } catch (e: IOException) {
            error = "Problem making the HTTP request."
        }
        return extractFeatureFromJson(jsonResponse)
    }

    @Throws(IOException::class)
    private fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        return try {
            val inputStream: InputStream = urlConnection.inputStream
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            val hasInput: Boolean = scanner.hasNext()
            if (hasInput) {
                scanner.next()
            } else {
                null
            }
        } finally {
            urlConnection.disconnect()
        }
    }

    private fun extractFeatureFromJson(newsJSON: String?): ArrayList<Movie>? {
        if (TextUtils.isEmpty(newsJSON)) {
            return null
        }
        val movieList: ArrayList<Movie> = ArrayList()
        try {
            val baseJsonResponse = JSONObject(newsJSON ?: "")
            val newsArray = baseJsonResponse.getJSONArray("results")

            for (i in 0 until newsArray.length()) {
                val currentMovieItem = newsArray.getJSONObject(i)
                val id = currentMovieItem.getInt("id")

                val title = currentMovieItem.getString("title")
                val plotSynopsis = currentMovieItem.getString("overview")
                val posterPath = currentMovieItem.getString("poster_path")
                val movieItem = Movie(
                    id, title, plotSynopsis, posterPath
                )
                movieList.add(movieItem)
            }
        } catch (e: JSONException) {
            error ="Problem parsing the news JSON results"
        }
        return movieList
    }
}