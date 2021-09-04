package io.daniilxt.feature.hot_gif.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.daniilxt.common.di.viewmodel.ViewModelKey
import io.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.hot_gif.presentation.HotGifViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class HotGifModule {

    @Provides
    @IntoMap
    @ViewModelKey(HotGifViewModel::class)
    fun provideViewModel(
    ): ViewModel {
        return HotGifViewModel()
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): HotGifViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(HotGifViewModel::class.java)
    }
}