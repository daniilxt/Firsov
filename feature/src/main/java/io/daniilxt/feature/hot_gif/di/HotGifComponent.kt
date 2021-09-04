package io.daniilxt.feature.hot_gif.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.daniilxt.common.di.scope.ScreenScope
import io.daniilxt.feature.hot_gif.presentation.HotGifFragment

@Subcomponent(
    modules = [
        HotGifModule::class,
    ]
)
@ScreenScope
interface HotGifComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): HotGifComponent
    }

    fun inject(fragment: HotGifFragment)
}