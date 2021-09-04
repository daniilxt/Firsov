package io.daniilxt.fintech.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.daniilxt.feature.FeatureRouter
import io.daniilxt.fintech.R
import timber.log.Timber

class Navigator : FeatureRouter {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attach(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    fun detach() {
        navController = null
        activity = null
    }

    private companion object {
        private val TAG = Navigator::class.simpleName
    }
}