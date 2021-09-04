package io.daniilxt.feature.data.remote.source

import io.daniilxt.feature.data.remote.api.FeatureApiService
import io.daniilxt.feature.data.source.FeatureDataSource
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {

}