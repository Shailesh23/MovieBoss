package com.movieboss

import com.movieboss.pojo.movies.MovieResult

interface MovieClickListener {
    fun movieSelected(movie : MovieResult)
}