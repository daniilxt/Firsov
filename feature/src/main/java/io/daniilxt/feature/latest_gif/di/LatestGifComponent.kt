package io.daniilxt.feature.latest_gif.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.daniilxt.common.di.scope.ScreenScope
import io.daniilxt.feature.latest_gif.presentation.LatestGifFragment

@Subcomponent(
    modules = [
        LatestGifModule::class,
    ]
)
@ScreenScope
interface LatestGifComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): LatestGifComponent
    }

    fun inject(fragment: LatestGifFragment)
}