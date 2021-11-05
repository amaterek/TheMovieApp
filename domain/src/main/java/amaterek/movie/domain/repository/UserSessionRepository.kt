package amaterek.movie.domain.repository

import amaterek.movie.domain.common.LoginResult
import amaterek.movie.domain.model.UserSessionState
import kotlinx.coroutines.flow.StateFlow

interface UserSessionRepository {

    val userSessionState: StateFlow<UserSessionState>

    suspend fun updateSessionState()

    suspend fun login(username: String, password: String): LoginResult

    suspend fun logout()
}
