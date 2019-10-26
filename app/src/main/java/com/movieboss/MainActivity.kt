package com.movieboss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieboss.adapters.HomeScreenAdapter
import com.movieboss.pojo.movies.popular.ResultPopular
import com.movieboss.viewmodels.HomeViewModel
import com.synnapps.carouselview.CarouselView

import kotlinx.android.synthetic.main.activity_main.*

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

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularMovieList.layoutManager = linearLayoutManager

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.popularMovies.observe(this,
            Observer<List<ResultPopular>> { popularMovieDataList ->
                val homeScreenAdapter = HomeScreenAdapter(this)
                homeScreenAdapter.setPopularMovies(popularMovieDataList)
                popularMovieList.adapter = homeScreenAdapter
                homeScreenAdapter.notifyDataSetChanged()

                carouselView.setImageListener { position, imageView ->
                    if (null != imageView)
                        Glide.with(this@MainActivity)
                            .load("https://image.tmdb.org/t/p/w500${popularMovieDataList[position+1].posterPath}")
                            .into(imageView)
                }
                carouselView.pageCount = 5
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
