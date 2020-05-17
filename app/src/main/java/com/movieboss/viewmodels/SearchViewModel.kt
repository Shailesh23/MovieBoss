package com.movieboss.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieboss.repository.MovieRepository

class SearchViewModel(application: Application) : AndroidViewModel(application){

    val repository = MovieRepository()
    val searchLiveData = repository.getSearchResults()

    fun getSearchReasults(searchParams: String) {
        repository.searchMovies(searchParams)
    }
}