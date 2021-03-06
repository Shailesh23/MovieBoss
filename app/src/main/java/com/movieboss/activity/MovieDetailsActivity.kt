package com.movieboss.activity

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.movieboss.R
import com.movieboss.analytics.Analytics
import com.movieboss.pojo.movies.GenresItem
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.utils.Constants.Companion.MOVIE_KEY
import com.movieboss.viewmodels.MovieDetailsViewModel

import kotlinx.android.synthetic.main.activity_movie_details_actviity.*
import kotlinx.android.synthetic.main.content_movie_details_actviity.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    private var movie: MovieResult? = null
    private val movieDetailsViewModel by viewModel<MovieDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details_actviity)
        setSupportActionBar(toolbar)
        Analytics.logScreenEvent(this)

        setupUI()
        movieDetailsViewModel.genres.observe(this,
            Observer { genresItems ->
                tv_genres.text = genresItems?.filter { movie?.genresId?.contains(it.id) == true}
                    ?.joinToString(transform = {it.name.toString()}, separator = ", ").toString()
            })
        fab.setOnClickListener { view ->
            if (movie != null) {
                Snackbar.make(view, "Added to your favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                movieDetailsViewModel.saveMovie(movie!!)
                Analytics.logMovieFavorite(movie?.title?: "N/A")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        supportActionBar?.hide()
    }

    private fun setupUI() {
        movie = intent?.getParcelableExtra(MOVIE_KEY)
        Analytics.logMovieDetail(movie?.title?: "N/A")
        if (movie != null) {
            Glide.with(this)
                .load(
                    "https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${movie?.posterPath}"
                )
                .into(backdrop_image_holder)

            movie_desc.text = movie?.overview
            vote_count.text = movie?.voteCount.toString()
            language_supported.text = movie?.originalLanguage?.toUpperCase(Locale.getDefault())
            release_date.text = movie?.releaseDate

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movie_details_bottom_sheet.outlineProvider = object : ViewOutlineProvider() {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline?.setRoundRect(0, 0, view!!.width, (view.height + 16), 16.0f)
                    }
                }
                movie_details_bottom_sheet.clipToOutline = true
            }

            tv_popularity.text = movie?.popularity.toString()
            tv_vote_count.text = movie?.voteCount.toString()
            tv_average_vote.text = movie?.voteAverage.toString()

            val adult = if (movie?.isAdult == true) "Yes" else "No"
            tv_adult.text = adult

        } else {
            //TODO show error
        }
    }
}
