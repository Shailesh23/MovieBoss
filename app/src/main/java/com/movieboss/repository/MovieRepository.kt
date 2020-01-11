package com.movieboss.repository

import androidx.lifecycle.MutableLiveData
import com.movieboss.pojo.movies.popular.Movies

class MovieRepository {
    val mutablePopularMovieInfo = MutableLiveData<Movies>()


}