package io.daniilxt.feature.data.remote.source

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.data.remote.api.FeatureApiService
import io.daniilxt.feature.data.remote.model.GifResponse
import io.daniilxt.feature.data.remote.model.toGifModelList
import io.daniilxt.feature.data.source.FeatureDataSource
import io.daniilxt.feature.domain.model.GifDetailsError
import io.daniilxt.feature.domain.model.GifModel
import io.reactivex.Single
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) :
    FeatureDataSource {

    override fun getLatestGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureApiService.getLatestGifList(page).map {
            getGifDataFromResponse(it)
        }
    }

    override fun getTopGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureApiService.getTopGifList(page).map {
            getGifDataFromResponse(it)
        }
    }

    override fun getHotGifList(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureApiService.getHotGifList(page).map {
            getGifDataFromResponse(it)
        }
    }

    private fun getGifDataFromResponse(response: Response<GifResponse>) = when {
        response.isSuccessful -> {
            val res = response.body()
            if (res != null) {
                RequestResult.Success(res.toGifModelList())
            } else {
                RequestResult.Error(GifDetailsError.Unknown)
            }
        }
        response.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
            RequestResult.Error(GifDetailsError.GifNotFound)
        }
        else -> {
            RequestResult.Error(GifDetailsError.Unknown)
        }
    }
}