package amaterek.movie.app.ui.common.model

import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieGenre
import amaterek.movie.domain.model.MovieRating
import androidx.compose.runtime.Stable
import java.util.*

@Stable
data class UiMovie(
    val id: Long,
    val title: String,
    val rating: MovieRating,
    val genres: List<MovieGenre>,
    val releaseDate: Date?,
    val posterUrl: String,
    val backdropUrl: String,
    val isFavorite: Boolean,
)

fun Movie.toUiModel() = UiMovie(
    id = id,
    title = title,
    rating = rating,
    genres = genres,
    releaseDate = releaseDate,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    isFavorite = isFavorite,
)

fun List<Movie>.toUiModel() = map { it.toUiModel() }