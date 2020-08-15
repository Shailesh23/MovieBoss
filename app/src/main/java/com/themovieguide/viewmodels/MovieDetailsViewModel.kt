package com.themovieguide.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.repository.MovieRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository = MovieRepository()
    val genres = movieRepository.fetchGenres()

    fun saveMovie(movieResult: MovieResult) {
        movieRepository.saveFavoriteMovie(movieResult)
    }
}