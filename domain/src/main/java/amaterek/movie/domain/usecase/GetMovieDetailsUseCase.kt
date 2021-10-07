package amaterek.movie.domain.usecase

import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieDetails
import amaterek.movie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movieId: Long): QueryResult<MovieDetails> =
        movieRepository.getMovieDetails(movieId)
}