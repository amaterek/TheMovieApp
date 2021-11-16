package amaterek.movie.app.ui.searchmovies

import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.base.LoadingState
import androidx.compose.runtime.Stable

@Stable
internal data class SearchMoviesState(
    val phrase: String,
    val movies: List<UiMovie>,
    val loadingState: LoadingState<Unit>,
)