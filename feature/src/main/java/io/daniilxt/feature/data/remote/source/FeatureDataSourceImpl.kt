package io.daniilxt.feature.data.remote.source

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.data.remote.api.FeatureApiService
import io.daniilxt.feature.data.remote.model.toGifModelList
import io.daniilxt.feature.data.source.FeatureDataSource
import io.daniilxt.feature.domain.model.GifDetailsError
import io.daniilxt.feature.domain.model.GifModel
import io.reactivex.Single
import java.net.HttpURLConnection
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {


    override fun getLatestGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureApiService.getLatestGifList(page).map {
            when {
                it.isSuccessful -> {
                    val res = it.body()
                    if (res != null) {
                        RequestResult.Success(res.toGifModelList())
                    } else {
                        RequestResult.Error(GifDetailsError.Unknown)
                    }
                }
                it.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
                    RequestResult.Error(GifDetailsError.GifNotFound)
                }
                else -> {
                    RequestResult.Error(GifDetailsError.Unknown)
                }
            }
        }
    }
}