package com.themovieguide.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.themovieguide.R
import com.themovieguide.utils.Constants
import com.themovieguide.utils.RemoteConfig
import com.themovieguide.utils.showMovieDetails
import com.themovieguide.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment() {
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        childFragmentManager.apply {
            beginTransaction().apply {
                add(R.id.movie_items_container, ListItemFragment.newInstance("upcoming", "Upcoming Movies", "movie"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("popular", "Popular Movies", "movie"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("now_playing", "Now Playing", "movie"), null)
                add(R.id.movie_items_container, ListItemFragment.newInstance("top_rated", "Top Movies", "movie"), null)
                commitAllowingStateLoss()
            }
        }
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupCarousel()
    }

    //todo move carousel inside fragment
    private fun setupCarousel() {
        val context = context ?: return
        viewModel.upComingMovieLiveData.observe(context as LifecycleOwner, Observer { movieResponse ->
            val upcomingMovies = movieResponse.filter { it.backdropPath != null }
            carouselView.setImageListener { position, imageView ->
                if (null != imageView)
                    Glide.with(context)
                        .load("https://image.tmdb.org/t/p/${RemoteConfig.fetchConfig(Constants.BACKDROP_SIZE_KEY)}" +
                                "${upcomingMovies[position].backdropPath}")
                        .into(imageView)
            }
            carouselView.setImageClickListener {
                showMovieDetails(upcomingMovies[it], context)
            }
            carouselView.pageCount = 5
        })
        viewModel.getUpcomingMovies()
    }
}