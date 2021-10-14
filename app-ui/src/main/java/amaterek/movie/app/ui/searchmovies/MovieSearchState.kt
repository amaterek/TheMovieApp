package amaterek.movie.app.ui.searchmovies

import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesState

data class MovieSearchState(
    val phrase: String,
    val moviesState: LoadingState<MoviesState>,
)