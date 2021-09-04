package io.daniilxt.feature.domain.repository

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.domain.model.GifModel
import io.reactivex.Single

interface FeatureRepository {
    fun getLatestGifList(page: Int): Single<RequestResult<List<GifModel>>>
    fun getTopGifList(page: Int): Single<RequestResult<List<GifModel>>>
    fun getHotGifList(page: Int): Single<RequestResult<List<GifModel>>>
}