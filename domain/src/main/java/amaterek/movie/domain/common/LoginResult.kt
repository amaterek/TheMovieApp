package amaterek.movie.domain.common

import amaterek.movie.domain.model.UserSession

sealed interface LoginResult {
    data class Success(val userSession: UserSession) : LoginResult
    data class Failure(val cause: FailureCause) : LoginResult
}