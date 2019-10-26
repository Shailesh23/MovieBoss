package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieboss.network.MoviesRequest

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    val popularMovies = MoviesRequest().getPopularMovies()
}