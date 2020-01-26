package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovieRequestPage = 1
    private var topMovieRequestPage = 1
    private val moviesRequest = MoviesRequest() //TODO; DI this
    val popularMovies : MutableLiveData<ArrayList<MovieResult>> = loadPopularMovies()
    val topMovies : MutableLiveData<ArrayList<MovieResult>> = loadTopMovies()

    fun loadPopularMovies(): MutableLiveData<ArrayList<MovieResult>> {
        return moviesRequest.getPopularMovies(popularMovieRequestPage++)
    }

    fun loadTopMovies() : MutableLiveData<ArrayList<MovieResult>> {
        return moviesRequest.getTopRatedMovies(topMovieRequestPage++)
    }
}