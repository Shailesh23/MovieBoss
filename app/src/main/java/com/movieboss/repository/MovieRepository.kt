package com.movieboss.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movieboss.db.GenresDao
import com.movieboss.db.MovieDao
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.movies.GenresItem
import com.movieboss.pojo.movies.MovieResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieRepository : KoinComponent {
    private val moviesRequest = MoviesRequest()
    private val popularMovies = MutableLiveData<java.util.ArrayList<MovieResult>>()
    private val topMovies = MutableLiveData<ArrayList<MovieResult>>()
    private val upComingMovies = MutableLiveData<ArrayList<MovieResult>>()
    private val searchResults = MutableLiveData<ArrayList<MovieResult>>()
    private val movieDao : MovieDao by inject()
    private val genresDao : GenresDao by inject()

    fun getSearchResults(): MutableLiveData<ArrayList<MovieResult>> {
        return searchResults
    }

    fun fetchPopularMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getPopularMovies(page, popularMovies)
        return popularMovies
    }

    fun fetchTopMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getTopRatedMovies(page, topMovies)
        return topMovies
    }

    fun fetchUpComingMovies(page: Int): MutableLiveData<ArrayList<MovieResult>> {
        moviesRequest.getUpComingMovies(page, upComingMovies)
        return upComingMovies
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