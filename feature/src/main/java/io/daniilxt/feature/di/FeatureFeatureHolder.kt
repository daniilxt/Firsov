package io.daniilxt.feature.di

import io.daniilxt.common.di.FeatureApiHolder
import io.daniilxt.common.di.FeatureContainer
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.feature.FeatureRouter
import javax.inject.Inject

@ApplicationScope
class FeatureFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val featureRouter: FeatureRouter
) : FeatureApiHolder(featureContainer) {
    override fun initializeDependencies(): Any {
        val deps = DaggerFeatureComponent_FeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerFeatureComponent.factory()
            .create(featureRouter, deps)
    }
}