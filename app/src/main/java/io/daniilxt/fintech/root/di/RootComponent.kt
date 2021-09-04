package io.daniilxt.fintech.root.di

import dagger.BindsInstance
import dagger.Component
import io.daniilxt.common.di.CommonApi
import io.daniilxt.common.di.scope.FeatureScope
import io.daniilxt.fintech.navigation.Navigator
import io.daniilxt.fintech.root.presentation.di.RootActivityComponent

@Component(
    dependencies = [
        RootDependencies::class
    ],
    modules = [
        RootFeatureModule::class
    ]
)
@FeatureScope
interface RootComponent {

    fun mainActivityComponentFactory(): RootActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance navigator: Navigator,
            deps: RootDependencies
        ): RootComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
        ]
    )
    interface RootFeatureDependenciesComponent : RootDependencies
}
