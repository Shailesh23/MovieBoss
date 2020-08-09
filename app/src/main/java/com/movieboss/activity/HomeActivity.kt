package com.movieboss.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.facebook.stetho.Stetho
import com.movieboss.R
import com.movieboss.analytics.Analytics
import com.movieboss.utils.Constants
import com.movieboss.utils.RemoteConfig
import com.movieboss.utils.showMovieDetails
import com.movieboss.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

//todo go back to dagger
//todo add view binding
//todo gradient home screen poster
//todo dark theme
//TODO add view binding
class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Stetho.initializeWithDefaults(applicationContext)

        actionBar?.title = resources.getString(R.string.app_name)
        Analytics.logScreenEvent(this)

        //fetch latest genres and save into db
        viewModel.updateGenres()

        setupCarousel()
        supportFragmentManager.apply {
            beginTransaction().apply {
                add(R.id.movie_items_container, MovieListFragment.newInstance("upcoming", "Upcoming Movies", "movie"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("popular", "Popular Movies", "movie"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("now_playing", "Now Playing", "movie"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("top_rated", "Top Movies", "movie"), null)
                commitAllowingStateLoss()
            }
        }
    }

    private fun setupCarousel() {
        viewModel.upComingMovieLiveData.observe(this, Observer { movieResponse ->
            val upcomingMovies = movieResponse.filter { it.backdropPath != null }
            carouselView.setImageListener { position, imageView ->
                if (null != imageView)
                    Glide.with(this@HomeActivity)
                        .load("https://image.tmdb.org/t/p/${RemoteConfig.fetchConfig(Constants.BACKDROP_SIZE_KEY)}" +
                                "${upcomingMovies[position].backdropPath}")
                        .into(imageView)
            }
            carouselView.setImageClickListener {
                showMovieDetails(upcomingMovies[it], this@HomeActivity)
            }
            carouselView.pageCount = 5
        })

        viewModel.getUpcomingMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_fav -> {
                val intent = Intent(this, TvViewActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
