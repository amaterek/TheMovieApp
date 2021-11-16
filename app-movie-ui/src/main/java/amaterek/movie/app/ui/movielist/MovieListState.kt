package amaterek.movie.app.ui.movielist

import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.base.LoadingState
import androidx.compose.runtime.Stable

@Stable
internal data class MovieListState(
    val movies: List<UiMovie>,
    val loadingState: LoadingState<Unit>,
)