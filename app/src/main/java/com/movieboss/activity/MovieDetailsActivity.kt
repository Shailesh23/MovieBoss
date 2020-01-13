package com.movieboss.activity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.movieboss.R
import com.movieboss.utils.Constants

import kotlinx.android.synthetic.main.activity_movie_details_actviity.*
import kotlinx.android.synthetic.main.content_movie_details_actviity.*

class MovieDetailsActivity : AppCompatActivity() {

    var movieName: String? = null

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
        Glide.with(this)
            .load(
                "https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${intent?.getStringExtra(
                    Constants.MOVIE_BACKDROP_PATH
                )}"
            )
            .into(backdrop_image_holder)
        movieName = intent?.getStringExtra(Constants.MOVIE_TITLE_KEY)
        movie_title.text = movieName
        supportActionBar?.title = movieName

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movie_desc.text = intent?.getStringExtra(Constants.MOVIE_TITLE_DESC_KEY)
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
