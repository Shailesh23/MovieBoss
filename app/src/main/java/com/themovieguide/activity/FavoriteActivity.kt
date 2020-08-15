package com.themovieguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.themovieguide.MovieClickListener
import com.themovieguide.R
import com.themovieguide.adapters.GridMovieAdapter
import com.themovieguide.analytics.Analytics
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.viewmodels.FavouriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(), MovieClickListener {
    private val favouriteViewModel by viewModel<FavouriteViewModel>()
    private val gridMovieAdapter = GridMovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        Analytics.logScreenEvent(this)
        favouriteViewModel.favouriteMovies?.observe(this,
            Observer {
                if (it.isEmpty()) {
                    empty_text.visibility = View.VISIBLE
                    grid_movie_list.visibility = View.GONE
                } else {
                    gridMovieAdapter.setGridMovie(it)
                    gridMovieAdapter.notifyDataSetChanged()
                    empty_text.visibility = View.GONE
                }
            })

        grid_movie_list.adapter = gridMovieAdapter
        grid_movie_list.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun movieSelected(movie: MovieResult) {
        Analytics.logMovieUnFavorite(movie.title ?: "N/A")
        favouriteViewModel.removeFavoriteMovie(movie)
    }
}
