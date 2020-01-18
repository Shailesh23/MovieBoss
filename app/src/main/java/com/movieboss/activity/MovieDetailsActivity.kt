package com.movieboss.activity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.movieboss.R
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.utils.Constants.Companion.MOVIE_KEY

import kotlinx.android.synthetic.main.activity_movie_details_actviity.*
import kotlinx.android.synthetic.main.content_movie_details_actviity.*
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    var movie: MovieResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details_actviity)
        setSupportActionBar(toolbar)

        setupUI()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Added to your favorites", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupUI() {
        movie = intent?.getParcelableExtra(MOVIE_KEY)
        if(movie != null) {
            Glide.with(this)
                .load(
                    "https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${movie?.backdropPath}"
                )
                .into(backdrop_image_holder)

            supportActionBar?.title = movie?.title
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            movie_desc.text = movie?.overview
            vote_count.text = movie?.voteCount.toString()
            language_supported.text = movie?.originalLanguage?.toUpperCase(Locale.getDefault())
            release_date.text = movie?.releaseDate

        } else {
            //TODO show error
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
