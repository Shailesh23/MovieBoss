package com.themovieguide.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovieguide.db.GenresDao
import com.themovieguide.db.MovieDao
import com.themovieguide.network.MoviesRequest
import com.themovieguide.pojo.movies.GenresItem
import com.themovieguide.pojo.movies.MovieResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepository : KoinComponent {
    private val moviesRequest = MoviesRequest()
    private val searchResults = MutableLiveData<ArrayList<MovieResult>>()
    private val movieDao : MovieDao by inject()
    private val genresDao : GenresDao by inject()

    fun getSearchResults(): MutableLiveData<ArrayList<MovieResult>> {
        return searchResults
    }

    fun fetchMovies(mediaType : String, page: Int, movieRequestType : String,
                    movieLiveData: MutableLiveData<ArrayList<MovieResult>>) {
        moviesRequest.fetchMovieData(page, movieRequestType, mediaType, movieLiveData)
    }

    fun saveFavoriteMovie(movie: MovieResult) {
        GlobalScope.launch {
            movieDao.insertMovie(movie)
        }
    }

    fun getFavoriteMovies(): LiveData<List<MovieResult>>? {
        return movieDao.getAllFavMovies()
    }


    fun searchMovies(searchParams : String): MutableLiveData<ArrayList<MovieResult>> {
        return moviesRequest.getSearchResults(searchParams, searchResults)
    }

    fun removeFavouriteMovie(movie : MovieResult) {
        GlobalScope.launch {
            movieDao.deleteMovie(movie)
        }
    }
    
    fun updateGenres() {
        moviesRequest.updateGenresInfo {genres ->
            if (genres.genres.isNullOrEmpty()) return@updateGenresInfo
            for(genre in genres.genres) {
                if(genre != null) {
                    GlobalScope.launch {
                        genresDao.insertGenres(genre)
                    }
                }
            }
        }
    }

    fun fetchGenres(): LiveData<List<GenresItem>> {
        return genresDao.getGenres()
    }
}