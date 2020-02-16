package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class FavouriteViewModel constructor(application: Application) : AndroidViewModel(application), KoinComponent {
    private val movieRepository by inject<MovieRepository>()

    val favouriteMovies = movieRepository.getFavoriteMovies()

    fun removeFavoriteMovie(movie : MovieResult) {
        movieRepository.removeFavouriteMovie(movie)
    }
}