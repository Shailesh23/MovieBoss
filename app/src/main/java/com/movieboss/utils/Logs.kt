package com.movieboss.utils

import android.util.Log

class Logs {
    companion object {
        var isLogEnabled = true

        fun e(tag: String, message: String) {
            if (isLogEnabled) {
                Log.e(tag, message)
            }
        }

        fun i(tag: String, message: String) {
            if (isLogEnabled) {
                Log.i(tag, message)
            }
        }

        fun v(tag: String, message: String) {
            if (isLogEnabled) {
                Log.v(tag, message)
            }
        }

        fun d(tag: String, message: String) {
            if (isLogEnabled) {
                Log.d(tag, message)
            }
        }

        fun w(tag: String, message: String) {
            if (isLogEnabled) {
                Log.w(tag, message)
            }
        }
    }
}