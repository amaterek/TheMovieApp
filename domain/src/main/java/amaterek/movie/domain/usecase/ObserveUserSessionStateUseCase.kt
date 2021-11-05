package amaterek.movie.domain.usecase

import amaterek.movie.domain.model.UserSessionState
import amaterek.movie.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveUserSessionStateUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {

    operator fun invoke(): StateFlow<UserSessionState> = userSessionRepository.userSessionState
}