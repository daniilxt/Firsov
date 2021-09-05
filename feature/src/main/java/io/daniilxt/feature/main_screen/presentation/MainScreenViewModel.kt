package io.daniilxt.feature.main_screen.presentation

import androidx.lifecycle.ViewModel
import io.daniilxt.feature.FeatureRouter

class MainScreenViewModel(
    private val router: FeatureRouter
) : ViewModel() {
    fun openProfileFragment() {
        router.openProfileFragment()
    }
}