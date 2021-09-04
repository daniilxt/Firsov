package io.daniilxt.feature.data.repository

import io.daniilxt.feature.data.source.FeatureDataSource
import io.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
}