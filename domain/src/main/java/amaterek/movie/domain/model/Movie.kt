package amaterek.movie.domain.model

import java.util.*

data class Movie(
    val id: Long,
    val title: String,
    val rating: MovieRating,
    val genres: List<MovieGenre>,
    val releaseDate: Date?,
    val posterUrl: String,
    val backdropUrl: String,
    val isFavorite: Boolean,
)