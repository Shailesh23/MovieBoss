package com.movieboss.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

class RemoteConfig {

    companion object {
        fun fetchConfig(configKey : String) : String {
            return Firebase.remoteConfig.getString(configKey)
        }
    }
}