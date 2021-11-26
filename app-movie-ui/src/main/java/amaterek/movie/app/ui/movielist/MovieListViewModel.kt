package amaterek.movie.app.ui.movielist

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.app.ui.common.model.UiMovieCategory
import amaterek.movie.app.ui.common.model.toDomain
import amaterek.movie.app.ui.common.model.toUiModel
import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesLoaderState
import amaterek.movie.base.transformValue
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
            category = UiMovieCategory.NOW_PLAYING,
            movies = emptyList(),
            loadingState = LoadingState.Idle(Unit)
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    private lateinit var moviesLoader: MoviesLoader

    init {
        initMoviesLoader(queryForCategory(stateFlow.value.category))
        observeFavoriteMoviesUseCase()
            .onEach(::handleFavoriteMoviesIds)
            .launchIn(viewModelScope)
    }

    private fun initMoviesLoader(query: MovieQuery) {
        moviesLoader = MoviesLoader.create(query, getMoviesPageUseCase)
        moviesLoader.stateFlow
            .onEach(::handleMoviesLoaderState)
            .launchIn(viewModelScope)
        viewModelScope.launch {
            moviesLoader.loadMore()
        }
    }

    private fun handleMoviesLoaderState(movieLoaderState: LoadingState<MoviesLoaderState>) {
        _stateFlow.value = stateFlow.value.copy(
            movies = movieLoaderState.value.movies.toUiModel(),
            loadingState = movieLoaderState.transformValue { }
        )
    }

    fun requestLoadMore() {
        viewModelScope.launch {
            moviesLoader.loadMore()
        }
    }

    fun setCategory(category: UiMovieCategory) {
        if (stateFlow.value.category == category) return
        Log.d(logTag(), "setCategory: $category")
        _stateFlow.value = stateFlow.value.copy(category = category)
        initMoviesLoader(queryForCategory(category))
    }

    private fun queryForCategory(category: UiMovieCategory) = MovieQuery(
        type = Type.ByCategory(category.toDomain()),
        sortBy = SortBy.POPULARITY_DESCENDING,
        genres = emptySet(), // All
    )

    private fun handleFavoriteMoviesIds(favoriteMoviesIds: Set<Long>) {
        moviesLoader.setFavoriteMoviesIds(favoriteMoviesIds)
    }
}