package com.movieboss

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movieboss.adapters.HomeScreenAdapter
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.Result
import com.movieboss.viewmodels.HomeViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    //TODO add data binding
    lateinit var viewModel: HomeViewModel
    lateinit var popularMovieList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        actionBar?.hide()
        supportActionBar?.hide()

        popularMovieList = findViewById(R.id.popular_movie_list)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularMovieList.layoutManager = linearLayoutManager

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.popularMovies.observe(this,
            Observer<List<Result>> { popularMovieDataList ->
                val homeScreenAdapter = HomeScreenAdapter(this)
                homeScreenAdapter.setPopularMovies(popularMovieDataList)
                popularMovieList.adapter = homeScreenAdapter

                homeScreenAdapter.notifyDataSetChanged()
            })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
