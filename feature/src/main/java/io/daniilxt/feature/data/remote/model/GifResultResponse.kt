package io.daniilxt.feature.data.remote.model

import com.google.gson.annotations.SerializedName
import io.daniilxt.feature.domain.model.GifModel

data class GifResultResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("votes") val votes: Int,
    @SerializedName("author") val author: String,
    @SerializedName("gifURL") val gifURL: String,
    @SerializedName("commentsCount") val commentsCount: Int,
)

fun GifResultResponse.toGifModel(): GifModel {
    return GifModel(
        id = this.id,
        description = this.description,
        votes = this.votes,
        author = this.author,
        gifURL = this.gifURL,
        commentsCount = this.commentsCount
    )
}