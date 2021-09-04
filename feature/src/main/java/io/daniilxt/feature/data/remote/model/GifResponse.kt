package io.daniilxt.feature.data.remote.model

import com.google.gson.annotations.SerializedName
import io.daniilxt.feature.domain.model.GifModel

data class GifResponse(

    @SerializedName("result") val result: List<GifResultResponse>,
    @SerializedName("totalCount") val totalCount: Int
)

fun GifResponse.toGifModelList(): List<GifModel> {
    return result.map { it.toGifModel() }
}