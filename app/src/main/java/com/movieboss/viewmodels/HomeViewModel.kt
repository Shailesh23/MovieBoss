package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val moviesRepo by inject<MovieRepository>()
    private val movieData: HashMap<String, MutableLiveData<ArrayList<MovieResult>>> = HashMap()
    private val moviePage: HashMap<String, Int> = HashMap()

    fun loadMovieData(movieRequestType: String) {
        val movieRequestPage = moviePage[movieRequestType] ?: 1
        moviesRepo.fetchMovies(movieRequestPage, movieRequestType, movieData[movieRequestType]!!)
        moviePage[movieRequestType] = movieRequestPage + 1
    }

    fun setupMovieInfo(movieRequestType: String) {
        movieData[movieRequestType] = MutableLiveData()
    }

    fun getMovieObserver(movieRequestType: String): MutableLiveData<ArrayList<MovieResult>>? {
        return movieData[movieRequestType]
    }
    fun updateGenres() {
        moviesRepo.updateGenres()
    }
}