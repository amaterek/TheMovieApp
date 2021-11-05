package amaterek.movie.app.usersession

import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.LoginResult
import amaterek.movie.domain.model.UserSession
import amaterek.movie.domain.model.UserSessionState
import amaterek.movie.domain.repository.UserSessionRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class AppUserSessionRepository @Inject constructor() : UserSessionRepository {

    private val _userSessionState = MutableStateFlow<UserSessionState>(UserSessionState.Unknown)

    override suspend fun updateSessionState() {
        if (_userSessionState.value == UserSessionState.Unknown) {
            delay(1000)
            _userSessionState.value = UserSessionState.LoggedOut
        }
    }

    override suspend fun login(username: String, password: String): LoginResult {
        if (_userSessionState.value is UserSessionState.LoggedIn) {
            return LoginResult.Failure(cause = FailureCause.Error)
        }

        val result = withContext(Dispatchers.IO) {
            delay(1000)
            val result = if (password.length <= 1) {
                LoginResult.Failure(cause = FailureCause.Failure)
            } else {
                LoginResult.Success(UserSession(username))
            }
            result
        }
        when (result) {
            is LoginResult.Success ->
                _userSessionState.value = UserSessionState.LoggedIn(result.userSession)
            is LoginResult.Failure ->
                _userSessionState.value = UserSessionState.LoggedOut
        }
        return result
    }

    override suspend fun logout() {
        delay(100)
        _userSessionState.value = UserSessionState.LoggedOut
    }

    override val userSessionState: StateFlow<UserSessionState>
        get() = _userSessionState
}