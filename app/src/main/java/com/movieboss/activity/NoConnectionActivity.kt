package com.movieboss.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

abstract class NoConnectionActivity : AppCompatActivity() {

    var isNetworkError = false

    override fun onStart() {

        super.onStart()

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkRequestBuild = NetworkRequest.Builder()
        connectivityManager?.requestNetwork(networkRequestBuild.build(), object :
            ConnectivityManager.NetworkCallback() {

            override fun onLosing(network: Network, maxMsToLive: Int) {
                showNoNetworkDialog()
                super.onLosing(network, maxMsToLive)
            }

            override fun onLost(network: Network) {
                showNoNetworkDialog()
                super.onLost(network)
            }

            override fun onUnavailable() {
                showNoNetworkDialog()
                super.onUnavailable()
            }

            override fun onAvailable(network: Network) {
                reloadData()
                super.onAvailable(network)
            }
        })

        if(isNetworkNotAvailable()) {
            showNoNetworkDialog()
        }
    }

    open fun isNetworkNotAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return !(connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo
            .isConnected)
    }

    abstract fun reloadData()

    private fun showNoNetworkDialog() {
        isNetworkError = true
        AlertDialog.Builder(this).setTitle("Connection Error")
            .setMessage("There is no internet available, Please connect to internet")
            .setPositiveButton("Okay", null).create().show()
    }
}