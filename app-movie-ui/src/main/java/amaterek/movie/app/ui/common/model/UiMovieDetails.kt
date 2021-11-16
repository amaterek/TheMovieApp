package amaterek.movie.app.ui.common.model

import amaterek.movie.domain.model.MovieDetails
import androidx.compose.runtime.Stable

@Stable
data class UiMovieDetails(
    val movie: UiMovie,
    val description: String,
    val budget: Long
)

fun MovieDetails.toUiModel() = UiMovieDetails(
    movie = movie.toUiModel(),
    description = description,
    budget = budget
)