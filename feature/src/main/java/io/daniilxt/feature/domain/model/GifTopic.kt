package io.daniilxt.feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GifTopic : Parcelable {
    LATEST,
    TOP,
    HOT
}