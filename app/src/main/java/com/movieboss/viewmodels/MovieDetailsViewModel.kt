package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository = MovieRepository()

    fun saveMovie(movieResult: MovieResult) {
        movieRepository.saveFavoriteMovie(movieResult)
    }

    fun getMovies() {
        movieRepository.getFavoriteMovies()
    }
}