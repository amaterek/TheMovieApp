package amaterek.movie.domain.usecase

import amaterek.movie.domain.repository.UserSessionRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) {

    suspend operator fun invoke() =
        userSessionRepository.logout()
}