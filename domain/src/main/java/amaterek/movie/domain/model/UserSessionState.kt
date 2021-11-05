package amaterek.movie.domain.model

sealed interface UserSessionState {

    object Unknown: UserSessionState

    object LoggedOut: UserSessionState

    data class LoggedIn(val userSession: UserSession): UserSessionState
}
