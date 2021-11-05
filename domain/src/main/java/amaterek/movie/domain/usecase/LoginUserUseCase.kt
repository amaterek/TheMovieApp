package amaterek.movie.domain.usecase

import amaterek.movie.domain.common.LoginResult
import amaterek.movie.domain.repository.UserSessionRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val userSessionRepository: UserSessionRepository) {

    suspend operator fun invoke(username: String, password: String): LoginResult =
        userSessionRepository.login(username, password)
}