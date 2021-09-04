package io.daniilxt.feature.latest_gif.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.daniilxt.common.di.viewmodel.ViewModelKey
import io.daniilxt.common.di.viewmodel.ViewModelModule
import io.daniilxt.feature.domain.usecase.GetLatestGifListUseCase
import io.daniilxt.feature.domain.usecase.GetTopGifListUseCase
import io.daniilxt.feature.latest_gif.presentation.LatestGifViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class LatestGifModule {
    @Provides
    @IntoMap
    @ViewModelKey(LatestGifViewModel::class)
    fun provideViewModel(
        getLatestGifListUseCase: GetLatestGifListUseCase,
        getTopGifListUseCase: GetTopGifListUseCase
    ): ViewModel {
        return LatestGifViewModel(getLatestGifListUseCase, getTopGifListUseCase)
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): LatestGifViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(LatestGifViewModel::class.java)
    }
}