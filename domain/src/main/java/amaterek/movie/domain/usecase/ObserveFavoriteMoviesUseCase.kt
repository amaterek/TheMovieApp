package amaterek.movie.domain.usecase

import amaterek.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveFavoriteMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(): StateFlow<Set<Long>> =
        movieRepository.favoriteMoviesIds
}