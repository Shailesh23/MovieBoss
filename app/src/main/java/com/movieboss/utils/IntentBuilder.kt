package com.movieboss.utils

import android.content.Context
import android.content.Intent
import com.movieboss.activity.MovieDetailsActivity
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants.Companion.MOVIE_BACKDROP_PATH
import com.movieboss.utils.Constants.Companion.MOVIE_ID_KEY
import com.movieboss.utils.Constants.Companion.MOVIE_POSTER_PATH
import com.movieboss.utils.Constants.Companion.MOVIE_RELEASE_DATE
import com.movieboss.utils.Constants.Companion.MOVIE_TITLE_DESC_KEY
import com.movieboss.utils.Constants.Companion.MOVIE_TITLE_KEY

fun showMovieDetails(movieResult: MovieResult, context: Context) {
    val intent = Intent(context, MovieDetailsActivity::class.java)
    intent.putExtra(MOVIE_TITLE_KEY, movieResult.title)
    intent.putExtra(MOVIE_TITLE_DESC_KEY, movieResult.overview)
    intent.putExtra(MOVIE_POSTER_PATH, movieResult.posterPath)
    intent.putExtra(MOVIE_BACKDROP_PATH, movieResult.backdropPath)
    intent.putExtra(MOVIE_ID_KEY, movieResult.id)
    intent.putExtra(MOVIE_RELEASE_DATE, movieResult.releaseDate)

    context.startActivity(intent)
}