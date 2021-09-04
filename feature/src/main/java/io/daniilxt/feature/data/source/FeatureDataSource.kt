package io.daniilxt.feature.data.source

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.domain.model.GifModel
import io.reactivex.Single

interface FeatureDataSource {
    fun getLatestGifList(page: Int): Single<RequestResult<List<GifModel>>>
    fun getTopGifList(page: Int): Single<RequestResult<List<GifModel>>>
}