package com.movieboss.repository

import androidx.lifecycle.MutableLiveData
import com.movieboss.pojo.movies.popular.MoviesInfoPopular

class MovieRepository {
    val mutablePopularMovieInfo = MutableLiveData<MoviesInfoPopular>()


}