package io.daniilxt.feature.database.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.model.GifTopic

@Entity(
    tableName = "gifs_table",
    indices = [Index(value = ["id"], unique = true)]
)
data class GifDataBaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val page: Int,
    val gifTopic: GifTopic,
    val description: String,
    val votes: Int,
    val author: String,
    val gifURL: String,
    val commentsCount: Int
) {
    constructor(
        page: Int,
        gifTopic: GifTopic,
        description: String,
        votes: Int,
        author: String,
        gifURL: String,
        commentsCount: Int
    ) : this(
        0, page,
        gifTopic,
        description,
        votes,
        author,
        gifURL,
        commentsCount
    )
}

fun GifDataBaseModel.toGifModel() = GifModel(
    id, description, votes, author, gifURL, commentsCount
)