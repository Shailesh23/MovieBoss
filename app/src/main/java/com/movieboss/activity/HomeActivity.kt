package com.movieboss.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.stetho.Stetho
import com.movieboss.R
import com.movieboss.adapters.MovieAdapter
import com.movieboss.analytics.Analytics
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.utils.HorizontalSpaceItemDecoration
import com.movieboss.utils.RemoteConfig
import com.movieboss.viewmodels.HomeViewModel
import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel

//todo go back to dagger
//todo add view binding
//todo gradient home screen poster
//todo dark theme
//TODO add view binding
class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModel<HomeViewModel>()
    private val horizontalSpacing = 15

//    lateinit var carouselView: CarouselView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Stetho.initializeWithDefaults(applicationContext)

        actionBar?.title = resources.getString(R.string.home_screen_title)
        Analytics.logScreenEvent(this)

       // carouselView = findViewById(R.id.carouselView)

        //fetch latest genres and save into db
        viewModel.updateGenres()

        supportFragmentManager.apply {
            beginTransaction().apply {
                add(R.id.movie_items_container, MovieListFragment.newInstance("now_playing", "Now Playing"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("upcoming", "Upcoming Movies"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("popular", "Popular Movies"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("top_rated", "Top Movies"), null)
                commitAllowingStateLoss()
            }
        }
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
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
