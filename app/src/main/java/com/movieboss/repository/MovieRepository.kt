package com.movieboss.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movieboss.db.MovieDao
import com.movieboss.db.MovieDatabase
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class MovieRepository : KoinComponent {
    private val moviesRequest = MoviesRequest()
    private val popularMovies = MutableLiveData<java.util.ArrayList<MovieResult>>()
    private val topMovies = MutableLiveData<ArrayList<MovieResult>>()
    private val upComingMovies = MutableLiveData<ArrayList<MovieResult>>()
    private val searchResults = MutableLiveData<ArrayList<MovieResult>>()
    private val movieDb : MovieDao by inject()

    fun getSearchResults(): MutableLiveData<ArrayList<MovieResult>> {
        return searchResults
    }

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

    fun saveFavoriteMovie(movie: MovieResult) {
        GlobalScope.launch {
            movieDb.insertMovie(movie)
        }
    }

    fun getFavoriteMovies(): LiveData<List<MovieResult>>? {
        return movieDb.getAllFavMovies()
    }


    fun searchMovies(searchParams : String): MutableLiveData<ArrayList<MovieResult>> {
        return moviesRequest.getSearchResults(searchParams, searchResults)
    }

    fun removeFavouriteMovie(movie : MovieResult) {
        GlobalScope.launch {
            movieDb.deleteMovie(movie)
        }
    }
}