package com.movieboss.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.movieboss.R
import com.movieboss.adapters.GridMovieAdapter
import com.movieboss.analytics.Analytics
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel by viewModel<SearchViewModel>()
    private val gridMovieAdapter = GridMovieAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        Analytics.logScreenEvent(this)

        grid_list.adapter = gridMovieAdapter
        grid_list.layoutManager = GridLayoutManager(this, 3)
        viewModel.searchLiveData.observe(this,
            Observer{ t ->
                if (t?.isEmpty() == true) {
                    empty_results.visibility = View.VISIBLE
                    empty_results.text = resources.getString(R.string.search_result_not_found)
                     grid_list.visibility = View.GONE
                } else {
                    gridMovieAdapter.setGridMovie(t.filter { !it.posterPath.isNullOrEmpty() })
                    gridMovieAdapter.notifyDataSetChanged()
                    empty_results.visibility = View.GONE
                    grid_list.visibility = View.VISIBLE
                }
            })

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_option_menu, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false // Do not iconify the widget; expand it by default
            setOnQueryTextListener(this@SearchActivity)
            maxWidth = Int.MAX_VALUE
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.getSearchReasults(newText ?: "")
        return true
    }
}
