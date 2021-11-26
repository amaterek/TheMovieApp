package amaterek.movie.app.ui.common.model

import amaterek.movie.domain.model.MovieCategory
import androidx.compose.runtime.Stable

@Stable
enum class UiMovieCategory {
    NOW_PLAYING,
    POPULAR,
    TOP_RATED,
    UPCOMING,
    FAVORITE
}

fun UiMovieCategory.toDomain() =
    when (this) {
        UiMovieCategory.NOW_PLAYING -> MovieCategory.NOW_PLAYING
        UiMovieCategory.POPULAR -> MovieCategory.POPULAR
        UiMovieCategory.TOP_RATED -> MovieCategory.TOP_RATED
        UiMovieCategory.UPCOMING -> MovieCategory.UPCOMING
        UiMovieCategory.FAVORITE -> MovieCategory.FAVORITE
    }