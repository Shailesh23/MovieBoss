package com.movieboss.activity

import android.content.Intent
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
import com.facebook.stetho.Stetho
import com.movieboss.R
import com.movieboss.adapters.MovieAdapter
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.viewmodels.HomeViewModel
import com.synnapps.carouselview.CarouselView
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), ViewCallback {
    //TODO add data binding
    private val viewModel by viewModel<HomeViewModel>()

    lateinit var carouselView: CarouselView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Stetho.initializeWithDefaults(applicationContext)

        supportActionBar?.title = resources.getString(R.string.home_screen_title)

        carouselView = findViewById(R.id.carouselView)

        val popularMovieAdapter = getMovieAdapter()
        popular_movie_list.adapter = popularMovieAdapter
        val popularMovieLayoutManager = getHorizontalLayoutManager()
        popular_movie_list.layoutManager = popularMovieLayoutManager

        val topRatedMovieAdapter = getMovieAdapter()
        top_rated_movie_list.adapter = topRatedMovieAdapter
        val topMovieLayoutManager = getHorizontalLayoutManager()
        top_rated_movie_list.layoutManager = topMovieLayoutManager

        val upComingMovieAdapter = getMovieAdapter()
        up_coming_movie_list.adapter = upComingMovieAdapter
        val upComingMovieLayoutManager = getHorizontalLayoutManager()
        up_coming_movie_list.layoutManager = upComingMovieLayoutManager

        viewModel.popularMovies.observe(this,
            Observer<List<MovieResult>> { popularMovieDataList ->
                popularMovieAdapter.setMovies(popularMovieDataList)
                popularMovieAdapter.notifyDataSetChanged()

                carouselView.setImageListener { position, imageView ->
                    if (null != imageView)
                        Glide.with(this@HomeActivity)
                            .load("https://image.tmdb.org/t/p/${Constants.BACKDROP_SIZE}${popularMovieDataList[position].backdropPath}")
                            .into(imageView)
                }
                carouselView.pageCount = 5
                hidePopularMovieProgressBar()
            })

        viewModel.topMovies.observe(this,
            Observer<List<MovieResult>> {
                topRatedMovieAdapter.setMovies(it)
                topRatedMovieAdapter.notifyDataSetChanged()

                hidePopularMovieProgressBar(1)
            })

        viewModel.upComingMovies.observe(this,
            Observer<List<MovieResult>> {
                upComingMovieAdapter.setMovies(it)
                upComingMovieAdapter.notifyDataSetChanged()

                hidePopularMovieProgressBar(2)
            })

        popular_movie_list.addOnScrollListener(
            MovieScrollListener(
                viewModel,
                0,
                popularMovieLayoutManager,
                this
            )
        )
        top_rated_movie_list.addOnScrollListener(
            MovieScrollListener(
                viewModel,
                1,
                topMovieLayoutManager,
                this
            )
        )
        up_coming_movie_list.addOnScrollListener(
            MovieScrollListener(
                viewModel,
                2,
                upComingMovieLayoutManager,
                this
            )
        )
    }

    private fun getHorizontalLayoutManager(): LinearLayoutManager {
        val topMovieLayoutManager = LinearLayoutManager(this)
        topMovieLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return topMovieLayoutManager
    }

    private fun getMovieAdapter(): MovieAdapter {
        return MovieAdapter(this)
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

            R.id.action_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showPopularMovieProgressBar(type: Int) {
        when (type) {
            0 -> popular_movie_progress_bar.visibility = View.VISIBLE
            2 -> up_coming_movie_progress_bar.visibility = View.VISIBLE
            else -> top_movie_progress_bar.visibility = View.VISIBLE
        }
    }

    override fun hidePopularMovieProgressBar(type: Int) {
        when (type) {
            0 -> popular_movie_progress_bar.visibility = View.GONE
            2 -> up_coming_movie_progress_bar.visibility = View.GONE
            else -> top_movie_progress_bar.visibility = View.GONE
        }
    }
}

class MovieScrollListener(
    private val homeViewModel: HomeViewModel,
    private val movieType: Int,
    private val layoutManager: LinearLayoutManager,
    private val viewCallBack: ViewCallback
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()

        if (lastItemVisible == totalItemCount - 1) {
            when (movieType) {
                0 -> {
                    viewCallBack.showPopularMovieProgressBar()
                    homeViewModel.loadPopularMovies()
                }
                1 -> {
                    viewCallBack.showPopularMovieProgressBar(1)
                    homeViewModel.loadTopMovies()
                }
                2 -> {
                    viewCallBack.showPopularMovieProgressBar(2)
                    homeViewModel.loadUpComingMovies()
                }
            }
        }

    }
}

interface ViewCallback {
    fun showPopularMovieProgressBar(type: Int = 0)
    fun hidePopularMovieProgressBar(type: Int = 0)
}
