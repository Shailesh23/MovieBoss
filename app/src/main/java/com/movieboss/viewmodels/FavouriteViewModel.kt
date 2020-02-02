package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.repository.MovieRepository

class FavouriteViewModel constructor(application: Application) : AndroidViewModel(application) {
    val movieRepository = MovieRepository()
    val favouriteMovies = movieRepository.getFavoriteMovies(application)

    fun removeFavoriteMovie(movie : MovieResult) {
        movieRepository.removeFavouriteMovie(movie, getApplication())
    }
}