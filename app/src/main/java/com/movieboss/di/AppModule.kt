package com.movieboss.di

import com.movieboss.db.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { MovieDatabase.getMovieDbInstance(androidContext()) }
}