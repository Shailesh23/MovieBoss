package com.movieboss.analytics

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class Analytics {
    companion object {
        private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

        fun logScreenEvent(activity: Activity) {
            firebaseAnalytics.setCurrentScreen(activity, null, null)
        }

        fun logMovieDetail(name: String) {
            val bundle = Bundle()
            bundle.putString("Movie", name)
            firebaseAnalytics.logEvent("Details", bundle)
        }

        fun logMovieFavorite(name: String) {
            val bundle = Bundle()
            bundle.putString("Movie", name)
            firebaseAnalytics.logEvent("Favorite", bundle)
        }

        fun logMovieUnFavorite(name: String) {
            val bundle = Bundle()
            bundle.putString("Movie", name)
            firebaseAnalytics.logEvent("Unfavorite", bundle)
        }
    }
}