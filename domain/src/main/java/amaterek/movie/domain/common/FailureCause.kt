package amaterek.movie.domain.common

sealed interface FailureCause {
    object NoConnection : FailureCause
    object Failure : FailureCause
    object Error : FailureCause
}
