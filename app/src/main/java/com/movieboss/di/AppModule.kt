package com.movieboss.di

import com.movieboss.db.MovieDatabase
import com.movieboss.repository.MovieRepository
import com.movieboss.viewmodels.FavouriteViewModel
import com.movieboss.viewmodels.HomeViewModel
import com.movieboss.viewmodels.MovieDetailsViewModel
import com.movieboss.viewmodels.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MovieDatabase.getMovieDbInstance(androidContext()) }
    viewModel { FavouriteViewModel(androidApplication()) }
    viewModel { HomeViewModel(androidApplication()) }
    viewModel { MovieDetailsViewModel(androidApplication()) }
    viewModel { SearchViewModel(androidApplication()) }
    single {  MovieRepository() }
}