package com.movieboss.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val movieRepository = MovieRepository()

    fun saveMovie(movieResult: MovieResult, context: Context) {
        movieRepository.saveFavoriteMovie(movieResult, context)
    }

    fun getMovies(context: Context) {
        movieRepository.getFavoriteMovies(context)
    }
}