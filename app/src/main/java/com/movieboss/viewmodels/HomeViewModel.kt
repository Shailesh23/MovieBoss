package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movieboss.network.MoviesRequest
import com.movieboss.pojo.MoviesInfo

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    val popularMovies = MoviesRequest().getPopularMovies()
}