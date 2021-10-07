package amaterek.movie.domain.common

sealed interface QueryResult<out T> {
    data class Success<T>(val value: T) : QueryResult<T>
    data class Failure(val cause: FailureCause) : QueryResult<Nothing>
}