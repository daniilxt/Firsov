package io.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifModel(
    val id: Int,
    val description: String,
    val votes: Int,
    val author: String,
    val gifURL: String,
    val commentsCount: Int,
) : Parcelable
//"date": "May 17, 2021 12:47:20 PM",
