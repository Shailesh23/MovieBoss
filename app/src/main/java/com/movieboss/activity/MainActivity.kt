package com.movieboss.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieboss.R
import com.movieboss.adapters.PopularMovieAdapter
import com.movieboss.adapters.TopMovieAdapter
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.viewmodels.HomeViewModel
import com.synnapps.carouselview.CarouselView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    //TODO add data binding
    lateinit var viewModel: HomeViewModel
    lateinit var popularMovieList: RecyclerView
    lateinit var carouselView: CarouselView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.title = resources.getString(R.string.home_screen_title)

        popularMovieList = findViewById(R.id.popular_movie_list)
        carouselView = findViewById(R.id.carouselView)

        val popularMovieLayoutManager = LinearLayoutManager(this)
        popularMovieLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularMovieList.layoutManager = popularMovieLayoutManager

        val topMovieLayoutManager = LinearLayoutManager(this)
        topMovieLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        top_rated_movie_list.layoutManager = topMovieLayoutManager

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.popularMovies.observe(this,
            Observer<List<MovieResult>> { popularMovieDataList ->
                val homeScreenAdapter = PopularMovieAdapter(this)
                homeScreenAdapter.setPopularMovies(popularMovieDataList)
                popularMovieList.adapter = homeScreenAdapter
                homeScreenAdapter.notifyDataSetChanged()

                carouselView.setImageListener { position, imageView ->
                    if (null != imageView)
                        Glide.with(this@MainActivity)
                            .load("https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${popularMovieDataList[position].backdropPath}")
                            .into(imageView)
                }
                carouselView.pageCount = 5
            })

        viewModel.topMovies.observe(this,
            Observer<List<MovieResult>> {
                val topRatedMoview = TopMovieAdapter(this, it)
                top_rated_movie_list.adapter = topRatedMoview
                topRatedMoview.notifyDataSetChanged()
            })
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
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
