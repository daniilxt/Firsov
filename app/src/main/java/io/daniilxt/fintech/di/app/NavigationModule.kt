package io.daniilxt.fintech.di.app

import dagger.Module
import dagger.Provides
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.feature.FeatureRouter
import io.daniilxt.fintech.navigation.Navigator

@Module
class NavigationModule {
    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideSplashRouter(navigator: Navigator): FeatureRouter = navigator
}