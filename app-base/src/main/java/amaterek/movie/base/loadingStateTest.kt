package amaterek.movie.base

import amaterek.movie.domain.common.FailureCause

sealed interface LoadingState<out T> {
    val value: T

    data class Idle<T>(override val value: T) : LoadingState<T>
    data class Loading<T>(override val value: T) : LoadingState<T>
    data class Failure<T>(override val value: T, val cause: FailureCause) : LoadingState<T>
}

fun <T> LoadingState<T>.copy(newValue: T): LoadingState<T> =
    when (this) {
        is LoadingState.Idle -> copy(value = newValue)
        is LoadingState.Loading -> copy(value = newValue)
        is LoadingState.Failure -> copy(value = newValue)
    }