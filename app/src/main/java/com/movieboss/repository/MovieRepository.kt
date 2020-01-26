package com.movieboss.repository

import androidx.lifecycle.MutableLiveData
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult

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
}