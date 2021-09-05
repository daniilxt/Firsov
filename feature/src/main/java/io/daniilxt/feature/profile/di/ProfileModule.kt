package io.daniilxt.feature.profile.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.daniilxt.common.di.viewmodel.ViewModelKey
import io.daniilxt.common.di.viewmodel.ViewModelModule
import io.daniilxt.feature.FeatureRouter
import io.daniilxt.feature.profile.presentation.ProfileViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class ProfileModule {

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return ProfileViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): ProfileViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}