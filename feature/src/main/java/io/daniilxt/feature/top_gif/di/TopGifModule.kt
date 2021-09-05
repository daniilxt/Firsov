package io.daniilxt.feature.top_gif.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.daniilxt.common.di.viewmodel.ViewModelKey
import io.daniilxt.common.di.viewmodel.ViewModelModule
import io.daniilxt.feature.domain.usecase.GetTopGifListUseCase
import io.daniilxt.feature.top_gif.presentation.TopGifViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class TopGifModule {

    @Provides
    @IntoMap
    @ViewModelKey(TopGifViewModel::class)
    fun provideViewModel(
        getTopGifListUseCase: GetTopGifListUseCase
    ): ViewModel {
        return TopGifViewModel(getTopGifListUseCase)
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): TopGifViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(TopGifViewModel::class.java)
    }
}