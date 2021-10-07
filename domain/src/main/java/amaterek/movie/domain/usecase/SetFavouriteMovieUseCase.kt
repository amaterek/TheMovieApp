package amaterek.movie.domain.usecase

import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.repository.MovieRepository
import javax.inject.Inject

class SetFavouriteMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie, isFavourite: Boolean): Unit =
        movieRepository.setFavorite(movie.id, isFavourite)
}