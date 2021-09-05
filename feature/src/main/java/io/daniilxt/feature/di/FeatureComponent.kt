package io.daniilxt.feature.di

import dagger.BindsInstance
import dagger.Component
import io.daniilxt.common.di.CommonApi
import io.daniilxt.common.di.scope.FeatureScope
import io.daniilxt.feature.FeatureRouter
import io.daniilxt.feature.hot_gif.di.HotGifComponent
import io.daniilxt.feature.latest_gif.di.LatestGifComponent
import io.daniilxt.feature.main_screen.di.MainScreenComponent
import io.daniilxt.feature.profile.di.ProfileComponent
import io.daniilxt.feature.top_gif.di.TopGifComponent

@Component(
    dependencies = [
        FeatureDependencies::class,
    ],
    modules = [
        FeatureModule::class,
        FeatureDataModule::class
    ]
)
@FeatureScope
interface FeatureComponent {

    fun mainScreenComponentFactory(): MainScreenComponent.Factory
    fun hotGifComponentFactory(): HotGifComponent.Factory
    fun latestGifComponentFactory(): LatestGifComponent.Factory
    fun topGifComponentFactory(): TopGifComponent.Factory
    fun profileComponentFactory(): ProfileComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance featureRouter: FeatureRouter,
            deps: FeatureDependencies
        ): FeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
        ]
    )
    interface FeatureDependenciesComponent : FeatureDependencies
}