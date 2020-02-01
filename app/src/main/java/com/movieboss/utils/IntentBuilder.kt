package com.movieboss.utils

import android.content.Context
import android.content.Intent
import com.movieboss.activity.MovieDetailsActivity
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants.Companion.MOVIE_KEY

fun showMovieDetails(movieResult: MovieResult, context: Context) {
    val intent = Intent(context, MovieDetailsActivity::class.java)
    intent.putExtra(MOVIE_KEY, movieResult)
    context.startActivity(intent)
}