package io.daniilxt.feature.top_gif.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.daniilxt.common.di.scope.ScreenScope
import io.daniilxt.feature.top_gif.presentation.TopGifFragment

@Subcomponent(
    modules = [
        TopGifModule::class,
    ]
)
@ScreenScope
interface TopGifComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): TopGifComponent
    }

    fun inject(fragment: TopGifFragment)
}