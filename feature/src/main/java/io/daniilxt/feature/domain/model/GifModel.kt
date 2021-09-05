package io.daniilxt.feature.domain.model

import android.os.Parcelable
import io.daniilxt.feature.database.models.GifDataBaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifModel(
    val id: Int,
    val description: String,
    val votes: Int,
    val author: String,
    val gifURL: String,
    val commentsCount: Int
) : Parcelable

fun GifModel.toGifDataBaseModel(pageNumber: Int, gifTopicType: GifTopic): GifDataBaseModel {
    return GifDataBaseModel(
        page = pageNumber,
        gifTopic = gifTopicType,
        description = this.description,
        votes = this.votes,
        author = this.author,
        gifURL = this.gifURL,
        commentsCount = this.commentsCount
    )
}
