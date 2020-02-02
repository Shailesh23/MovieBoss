package com.movieboss.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.movieboss.MovieClickListener
import com.movieboss.R
import com.movieboss.adapters.FavouriteMovieAdapter
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.viewmodels.FavouriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), MovieClickListener {

    private lateinit var favouriteViewModel : FavouriteViewModel
    private val favouriteMovieAdapter = FavouriteMovieAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel::class.java)
        favouriteViewModel.favouriteMovies?.observe(this,
            Observer<List<MovieResult>> {
                if(it.isEmpty()) {
                    no_fav_text.visibility = View.VISIBLE
                } else {
                    favouriteMovieAdapter.setFavourite(it)
                    favouriteMovieAdapter.notifyDataSetChanged()
                    no_fav_text.visibility = View.GONE
                }
            })

        favorite_list.adapter = favouriteMovieAdapter
        favorite_list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun movieSelected(movie: MovieResult) {
        favouriteViewModel.removeFavoriteMovie(movie)
    }

}
