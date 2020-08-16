package com.themovieguide.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themovieguide.R
import com.themovieguide.adapters.MovieAdapter
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.utils.HorizontalSpaceItemDecoration
import com.themovieguide.viewmodels.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val REQUEST_TYPE_KEY = "param1"
private const val DISPLAY_LABEL = "param2"
private const val TYPE = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [ListItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListItemFragment : Fragment() {

    private var requestType: String = ""
    private var label: String? = null
    private var type: String? = null
    lateinit var movieList : RecyclerView
    private val horizontalSpacing = 15
    private val viewModel by viewModel<HomeViewModel>()
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            requestType = it.getString(REQUEST_TYPE_KEY)!!
            label = it.getString(DISPLAY_LABEL)
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        view.findViewById<TextView>(R.id.movie_type_label).text = label
        movieList = view.findViewById(R.id.movie_list)
        progressBar = view.findViewById(R.id.movie_progress_bar)
        init()
        return view
    }

    private fun init() {
        val context = context
        if(context != null) {
            val movieListAdapter = MovieAdapter(context)
            movieList.adapter = movieListAdapter

            val movieLayoutManager = LinearLayoutManager(context)
            movieLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            movieList.layoutManager = movieLayoutManager

            movieList.addItemDecoration(HorizontalSpaceItemDecoration(horizontalSpacing))

            viewModel.setupMovieInfo(requestType)
            handleRequestNewPage()

            viewModel.getMovieObserver(requestType)?.observe(this, Observer<List<MovieResult>> { movieListAdapter.setMovies(it)
                movieListAdapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE

            })

            movieList.addOnScrollListener(MovieScrollListener(movieLayoutManager,
                ::handleRequestNewPage))
        }
    }

    private fun handleRequestNewPage() {
        viewModel.loadMovieData(requestType, type ?: "movie")
        progressBar.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param requestType Parameter 1.
         * @param label Parameter 2.
         * @return A new instance of fragment MovieListFragment.
         */
        @JvmStatic
        fun newInstance(requestType: String, label: String, type : String) =
            ListItemFragment().apply {
                arguments = Bundle().apply {
                    putString(REQUEST_TYPE_KEY, requestType)
                    putString(DISPLAY_LABEL, label)
                    putString(TYPE, type)
                }
            }
    }
}

class MovieScrollListener(private val layoutManager: LinearLayoutManager,
                            private val endOfPageListener : () -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()

        if (lastItemVisible == totalItemCount - 1) {
            endOfPageListener.invoke()
        }
    }
}