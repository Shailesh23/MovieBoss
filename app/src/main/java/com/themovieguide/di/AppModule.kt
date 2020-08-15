package com.themovieguide.di

import com.themovieguide.db.GenresDb
import com.themovieguide.db.MovieDatabase
import com.themovieguide.repository.MovieRepository
import com.themovieguide.viewmodels.FavouriteViewModel
import com.themovieguide.viewmodels.HomeViewModel
import com.themovieguide.viewmodels.MovieDetailsViewModel
import com.themovieguide.viewmodels.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MovieDatabase.getMovieDbInstance(androidContext()) }
    single { GenresDb.getGenresDbInstance(androidContext()) }
    viewModel { FavouriteViewModel(androidApplication()) }
    viewModel { HomeViewModel(androidApplication()) }
    viewModel { MovieDetailsViewModel(androidApplication()) }
    viewModel { SearchViewModel(androidApplication()) }
    single {  MovieRepository() }
}