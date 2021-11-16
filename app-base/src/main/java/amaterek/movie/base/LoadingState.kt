package amaterek.movie.base

import amaterek.movie.domain.common.FailureCause

sealed interface LoadingState<out T> {
    val value: T

    data class Idle<T>(override val value: T) : LoadingState<T>
    data class Loading<T>(override val value: T) : LoadingState<T>
    data class Failure<T>(override val value: T, val cause: FailureCause) : LoadingState<T>
}

fun <T, R> LoadingState<T>.transformValue(transform: T.() -> R): LoadingState<R> =
    when (this) {
        is LoadingState.Idle -> LoadingState.Idle(value = value.transform())
        is LoadingState.Loading -> LoadingState.Loading(value = value.transform())
        is LoadingState.Failure -> LoadingState.Failure(value = value.transform(), cause = cause)
    }