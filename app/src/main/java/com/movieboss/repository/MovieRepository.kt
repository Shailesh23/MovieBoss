package com.movieboss.repository

import androidx.lifecycle.MutableLiveData
import com.movieboss.pojo.MoviesInfo

class MovieRepository {
    val mutablePopularMovieInfo = MutableLiveData<MoviesInfo>()


}