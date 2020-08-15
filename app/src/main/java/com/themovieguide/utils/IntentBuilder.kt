package com.themovieguide.utils

import android.content.Context
import android.content.Intent
import com.themovieguide.activity.MovieDetailsActivity
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.utils.Constants.Companion.MOVIE_KEY

fun showMovieDetails(movieResult: MovieResult, context: Context) {
    val intent = Intent(context, MovieDetailsActivity::class.java)
    intent.putExtra(MOVIE_KEY, movieResult)
    context.startActivity(intent)
}