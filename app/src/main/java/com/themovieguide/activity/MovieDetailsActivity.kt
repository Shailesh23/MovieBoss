package com.themovieguide.activity

import android.content.ComponentName
import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.themovieguide.R
import com.themovieguide.analytics.Analytics
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.pojo.movies.VideoInfo
import com.themovieguide.utils.Constants
import com.themovieguide.utils.Constants.Companion.MOVIE_KEY
import com.themovieguide.utils.RemoteConfig
import com.themovieguide.viewmodels.MovieDetailsViewModel
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
        Analytics.logScreenEvent(this)

        setupUI()
        movieDetailsViewModel.getVideoInfo(
            ::handleVideoResult,
            movie?.id.toString(),
            if (movie?.isTvSeries == true) "tv" else "movie"
        )
        movieDetailsViewModel.genres.observe(this,
            Observer { genresItems ->
                tv_genres.text = genresItems?.filter { movie?.genresId?.contains(it.id) == true }
                    ?.joinToString(transform = { it.name.toString() }, separator = ", ").toString()
            })
        fab.setOnClickListener { view ->
            if (movie != null) {
                Snackbar.make(view, "Added to your favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                movieDetailsViewModel.saveMovie(movie!!)
                Analytics.logMovieFavorite(movie?.title ?: "N/A")
            }
        }
    }

    fun handleVideoResult(info: VideoInfo) {
        if (info.results?.isNotEmpty() == true && !info.results[0]?.key.isNullOrEmpty()) {
            iv_trailer.visibility = View.VISIBLE
            iv_trailer.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=${info.results[0]?.key}")
                )
                startActivity(intent)
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
        Analytics.logMovieDetail(movie?.title ?: "N/A")
        if (movie != null) {
            Glide.with(this)
                .load(
                    "https://image.tmdb.org/t/p/${RemoteConfig.fetchConfig(Constants.BACKDROP_SIZE_KEY)}" +
                            "${movie?.posterPath}"
                )
                .into(backdrop_image_holder)

            movie_desc.text = movie?.overview
            vote_count.text = movie?.voteCount.toString()
            language_supported.text = movie?.originalLanguage?.toUpperCase(Locale.getDefault())
            release_date.text = movie?.releaseDate

            movie_details_bottom_sheet.outlineProvider = object : ViewOutlineProvider() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view!!.width, (view.height + 16), 16.0f)
                }
            }
            movie_details_bottom_sheet.clipToOutline = true

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
