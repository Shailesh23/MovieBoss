package com.themovieguide

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.themovieguide.di.appModule
import com.themovieguide.utils.Constants
import com.themovieguide.utils.Logs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(Constants.fetchDefaultConfig())
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logs.i("MovieApplication" , "Config loaded")
                } else {
                    Logs.e("MovieApplication" , "Error loading config")
                }
            }
        startKoin {
            androidContext(this@MovieApplication)
            modules(appModule)
        }
    }
}