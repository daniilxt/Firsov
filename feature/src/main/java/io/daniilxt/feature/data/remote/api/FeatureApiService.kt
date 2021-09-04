package io.daniilxt.feature.data.remote.api

import io.daniilxt.feature.data.remote.model.GifResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FeatureApiService {
    @GET("latest/{page}?json=true")
    fun getLatestGifList(@Path("page") page: Int): Single<Response<GifResponse>>

    @GET("top/{page}?json=true")
    fun getTopGifList(@Path("page") page: Int): Single<Response<GifResponse>>

    @GET("hot/{page}?json=true")
    fun getHotGifList(@Path("page") page: Int): Single<Response<GifResponse>>
}