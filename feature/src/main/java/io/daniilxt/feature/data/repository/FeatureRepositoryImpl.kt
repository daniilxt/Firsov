package io.daniilxt.feature.data.repository

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.data.source.FeatureDataSource
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.repository.FeatureRepository
import io.reactivex.Single
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
    override fun getLatestGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureDataSource.getLatestGifList(page)
    }
    override fun getTopGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureDataSource.getTopGifList(page)
    }
    override fun getHotGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureDataSource.getHotGifList(page)
    }
}