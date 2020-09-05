package com.themovieguide.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.pojo.movies.VideoInfo
import com.themovieguide.repository.MovieRepository

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository = MovieRepository()
    val genres = movieRepository.fetchGenres()

    fun saveMovie(movieResult: MovieResult) {
        movieRepository.saveFavoriteMovie(movieResult)
    }

    fun getVideoInfo(handleResult : (VideoInfo) -> Unit, mediaId : String) {
        //make "movie" dynamic to take tv info also
        movieRepository.getVideoInfo(handleResult, "movie", mediaId)
    }
}