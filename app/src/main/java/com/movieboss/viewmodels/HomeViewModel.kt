package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var popularMovieRequestPage = 1
    private var topMovieRequestPage = 1
    private var upComingMovieRequestPage = 1
    private val moviesRepo = MovieRepository()

    val popularMovies : MutableLiveData<ArrayList<MovieResult>> = loadPopularMovies()
    val topMovies : MutableLiveData<ArrayList<MovieResult>> = loadTopMovies()
    val upComingMovies : MutableLiveData<ArrayList<MovieResult>> = loadUpComingMovies()

    fun loadPopularMovies(): MutableLiveData<ArrayList<MovieResult>> {
        return moviesRepo.fetchPopularMovies(popularMovieRequestPage++)
    }

    fun loadTopMovies() : MutableLiveData<ArrayList<MovieResult>> {
        return moviesRepo.fetchTopMovies(topMovieRequestPage++)
    }

    fun loadUpComingMovies() : MutableLiveData<ArrayList<MovieResult>> {
        return moviesRepo.fetchUpComingMovies(upComingMovieRequestPage++)
    }
}