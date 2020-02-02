package com.movieboss.repository

import android.content.Context
import androidx.lifecycle.LiveData
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
    private val upComingMovies = MutableLiveData<ArrayList<MovieResult>>()

    fun fetchPopularMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getPopularMovies(page, popularMovies)
        return popularMovies
    }

    fun fetchTopMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getTopRatedMovies(page, topMovies)
        return topMovies
    }

    fun fetchUpComingMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getUpComingMovies(page, upComingMovies)
        return upComingMovies
    }

    fun saveFavoriteMovie(movie: MovieResult, context: Context) {
        GlobalScope.launch {
            val movieDb = MovieDatabase.getMovieDbInstance(context)
            movieDb?.insertMovie(movie)
        }
    }

    fun getFavoriteMovies(context: Context): LiveData<List<MovieResult>>? {
        val movieDb = MovieDatabase.getMovieDbInstance(context)
        return movieDb?.getAllFavMovies()
    }

    fun removeFavouriteMovie(movie : MovieResult, context: Context) {
        GlobalScope.launch {
            val movieDb = MovieDatabase.getMovieDbInstance(context)
            movieDb?.deleteMovie(movie)
        }
    }
}