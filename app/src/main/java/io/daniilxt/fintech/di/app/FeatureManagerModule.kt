package io.daniilxt.fintech.di.app

import dagger.Module
import dagger.Provides
import io.daniilxt.common.di.FeatureApiHolder
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.fintech.di.deps.FeatureHolderManager

@Module
class FeatureManagerModule {
    @ApplicationScope
    @Provides
    fun provideFeatureHolderManager(featureApiHolderMap: @JvmSuppressWildcards Map<Class<*>, FeatureApiHolder>): FeatureHolderManager {
        return FeatureHolderManager(featureApiHolderMap)
    }
}
