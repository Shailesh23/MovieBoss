package com.themovieguide

import com.themovieguide.pojo.movies.MovieResult

interface MovieClickListener {
    fun movieSelected(movie : MovieResult)
}