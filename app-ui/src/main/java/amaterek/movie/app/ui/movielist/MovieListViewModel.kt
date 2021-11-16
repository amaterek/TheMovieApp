package amaterek.movie.app.ui.movielist

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.movie.app.ui.common.model.toUiModel
import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesLoaderState
import amaterek.movie.base.transformValue
import amaterek.movie.domain.model.MovieCategory
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.model.MovieQuery.SortBy
import amaterek.movie.domain.model.MovieQuery.Type
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import amaterek.movie.domain.usecase.ObserveFavoriteMoviesUseCase
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieListViewModel @Inject constructor(
    private val getMoviesPageUseCase: GetMoviesPageUseCase,
    observeFavoriteMoviesUseCase: ObserveFavoriteMoviesUseCase,
) : BaseViewModel() {

    private val _stateFlow = MutableStateFlow(
        MovieListState(
            movies = emptyList(),
            loadingState = LoadingState.Idle(Unit)
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    private var query = MovieQuery(
        type = Type.ByCategory(MovieCategory.NOW_PLAYING),
        sortBy = SortBy.POPULARITY_DESCENDING,
        genres = emptySet(), // All
    )

    private lateinit var moviesLoader: MoviesLoader

    init {
        initMoviesLoader()
        observeFavoriteMoviesUseCase()
            .onEach(::handleFavoriteMoviesIds)
            .launchIn(viewModelScope)
    }

    private fun initMoviesLoader() {
        moviesLoader = MoviesLoader.create(query, getMoviesPageUseCase)
        moviesLoader.stateFlow
            .onEach(::handleMoviesLoaderState)
            .launchIn(viewModelScope)
        viewModelScope.launch {
            moviesLoader.loadMore()
        }
    }

    private fun handleMoviesLoaderState(movieLoaderState: LoadingState<MoviesLoaderState>) {
        _stateFlow.value = MovieListState(
            movies = movieLoaderState.value.movies.toUiModel(),
            loadingState = movieLoaderState.transformValue { }
        )
    }

    fun requestLoadMore() {
        viewModelScope.launch {
            moviesLoader.loadMore()
        }
    }

    private fun handleFavoriteMoviesIds(favoriteMoviesIds: Set<Long>) {
        moviesLoader.setFavoriteMoviesIds(favoriteMoviesIds)
    }
}