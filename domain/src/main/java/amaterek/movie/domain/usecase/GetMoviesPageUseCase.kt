package amaterek.movie.domain.usecase

import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieList
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesPageUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(
        query: MovieQuery,
        page: Int
    ): QueryResult<MovieList> =
        movieRepository.getMovieListPage(
            query = query,
            page = page
        )
}