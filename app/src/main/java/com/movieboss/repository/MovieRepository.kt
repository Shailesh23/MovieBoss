package com.movieboss.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.movieboss.db.MovieDatabase
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieRepository {
    private val moviesRequest = MoviesRequest() //TODO; DI this
    private val popularMovies = MutableLiveData<java.util.ArrayList<MovieResult>>()
    private val topMovies = MutableLiveData<ArrayList<MovieResult>>()

    fun fetchPopularMovies(page : Int) : MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getPopularMovies(page, popularMovies)
        return popularMovies
    }

    fun fetchTopMovies(page : Int) : MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getTopRatedMovies(page, topMovies)
        return topMovies
    }

    fun saveFavoriteMovie(movie : MovieResult, context: Context) {
        GlobalScope.launch {
            val movieDb = MovieDatabase.getMovieDbInstance(context)
            movieDb?.insertMovie(movie)
        }
    }

    fun getFavoriteMovies(context: Context) {
        GlobalScope.launch {
            val movieDb = MovieDatabase.getMovieDbInstance(context)
            movieDb?.getAllFavMovies()
        }
    }
}