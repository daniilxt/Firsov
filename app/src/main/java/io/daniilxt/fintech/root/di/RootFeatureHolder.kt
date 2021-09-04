package io.daniilxt.fintech.root.di

import io.daniilxt.common.di.FeatureApiHolder
import io.daniilxt.common.di.FeatureContainer
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.fintech.navigation.Navigator
import javax.inject.Inject

@ApplicationScope
class RootFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val navigator: Navigator
) : FeatureApiHolder(featureContainer) {

    override fun initializeDependencies(): Any {
        val rootFeatureDependencies = DaggerRootComponent_RootFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .build()
        return DaggerRootComponent.factory()
            .create(navigator, rootFeatureDependencies)
    }
}
