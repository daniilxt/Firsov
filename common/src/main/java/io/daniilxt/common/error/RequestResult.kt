package io.daniilxt.common.error

sealed class RequestResult<T> {
    data class Success<T>(val data: T): RequestResult<T>()
    data class Error<T>(val error: ErrorEntity): RequestResult<T>()
}
