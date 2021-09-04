package io.daniilxt.feature.domain.model

import io.daniilxt.common.error.ErrorEntity

sealed class GifDetailsError : ErrorEntity {
    object GifNotFound : GifDetailsError()
    object Unknown : GifDetailsError()
}