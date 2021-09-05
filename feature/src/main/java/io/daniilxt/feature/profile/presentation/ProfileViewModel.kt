package io.daniilxt.feature.profile.presentation

import androidx.lifecycle.ViewModel
import io.daniilxt.feature.FeatureRouter

class ProfileViewModel(
    private val router: FeatureRouter
) : ViewModel() {
    fun onBackPressed() {
        router.back()
    }
}