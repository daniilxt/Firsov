package io.daniilxt.fintech.di.deps

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import io.daniilxt.common.di.FeatureApiHolder
import io.daniilxt.common.di.FeatureContainer
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureFeatureHolder
import io.daniilxt.fintech.App
import io.daniilxt.fintech.root.di.RootApi
import io.daniilxt.fintech.root.di.RootFeatureHolder

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(RootApi::class)
    @IntoMap
    fun provideMainFeature(rootFeatureHolder: RootFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(FeatureApi::class)
    @IntoMap
    fun provideFeatureFeature(featureHolder: FeatureFeatureHolder): FeatureApiHolder
}