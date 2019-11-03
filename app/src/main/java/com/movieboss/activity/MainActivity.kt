package com.movieboss.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ViewCallback {
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

        //TODO : check out movie list animations
        popularMovieList.layoutManager = popularMovieLayoutManager
        val homeScreenAdapter = PopularMovieAdapter(this)
        popularMovieList.adapter = homeScreenAdapter

        val topMovieLayoutManager = LinearLayoutManager(this)
        topMovieLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        top_rated_movie_list.layoutManager = topMovieLayoutManager
        top_rated_movie_list.itemAnimator = SlideInUpAnimator()
        val topRatedMovie = TopMovieAdapter(this)
        top_rated_movie_list.adapter = topRatedMovie


        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.popularMovies.observe(this,
            Observer<List<MovieResult>> { popularMovieDataList ->
                homeScreenAdapter.setPopularMovies(popularMovieDataList)
                homeScreenAdapter.notifyDataSetChanged()

                carouselView.setImageListener { position, imageView ->
                    if (null != imageView)
                        Glide.with(this@MainActivity)
                            .load("https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${popularMovieDataList[position].backdropPath}")
                            .into(imageView)
                }
                carouselView.pageCount = 5
                hidePopularMovieProgressBar()
            })

        viewModel.topMovies.observe(this,
            Observer<List<MovieResult>> {
                topRatedMovie.setTopMovies(it)
                topRatedMovie.notifyDataSetChanged()

                hidePopularMovieProgressBar(1)
            })

        popularMovieList.addOnScrollListener(MovieScrollListener(viewModel, 0, popularMovieLayoutManager, this))
        top_rated_movie_list.addOnScrollListener(MovieScrollListener(viewModel, 1, topMovieLayoutManager, this))
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

    override fun showPopularMovieProgressBar(type : Int) {
        if(type == 0) {
            popular_movie_progress_bar.visibility = View.VISIBLE
        } else {
            top_movie_progress_bar.visibility = View.VISIBLE
        }
    }

    override fun hidePopularMovieProgressBar(type : Int) {
        if(type == 0) {
            popular_movie_progress_bar.visibility = View.GONE
        } else {
            top_movie_progress_bar.visibility = View.GONE
        }
    }
}

class MovieScrollListener(
    val homeViewModel: HomeViewModel, val movieType: Int,
    val layoutManager: LinearLayoutManager,
    val viewCallBack : ViewCallback
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()

        if(lastItemVisible == totalItemCount-1) {
            when(movieType) {
                0 -> {
                    viewCallBack.showPopularMovieProgressBar()
                    homeViewModel.loadPopularMovies()
                }
                1-> {
                    viewCallBack.showPopularMovieProgressBar(1)
                    homeViewModel.loadTopMovies()
                }
            }
        }

    }
}

interface ViewCallback {
    fun showPopularMovieProgressBar(type : Int = 0)
    fun hidePopularMovieProgressBar(type : Int = 0)
}
