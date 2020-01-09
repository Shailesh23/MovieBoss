package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovieReqestPage = 0
    private var topMovieReqestPage = 0
    private val moviesRequest = MoviesRequest() //TODO; DI this
    val popularMovies : MutableLiveData<ArrayList<MovieResult>> = loadPopularMovies()
    val topMovies : MutableLiveData<ArrayList<MovieResult>> = loadTopMovies()

    fun loadPopularMovies(): MutableLiveData<ArrayList<MovieResult>> {
        popularMovieReqestPage++
        return moviesRequest.getPopularMovies(popularMovieReqestPage)
    }

    fun loadTopMovies() : MutableLiveData<ArrayList<MovieResult>> {
        topMovieReqestPage++
        return moviesRequest.getTopRatedMovies(topMovieReqestPage)
    }
}