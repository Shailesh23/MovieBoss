package com.themovieguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.themovieguide.R
import com.themovieguide.analytics.Analytics

class TvViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_view)

        actionBar?.title = resources.getString(R.string.tv_guide)
        Analytics.logScreenEvent(this)

//        setupCarousel()

        supportFragmentManager.apply {
            beginTransaction().apply {
                add(R.id.movie_items_container, MovieListFragment.newInstance("airing_today", "Airing Today", "tv"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("popular", "Popular", "tv"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("on_the_air", "On Air", "tv"), null)
                add(R.id.movie_items_container, MovieListFragment.newInstance("top_rated", "Top Movies", "tv"), null)
                commitAllowingStateLoss()
            }
        }
    }
}