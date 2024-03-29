package amaterek.movie.base.moviesloader

import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.base.LoadingState
import amaterek.movie.base.transformValue
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieList
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MoviesLoader private constructor(
    private val query: MovieQuery,
    private val getMoviesPageUseCase: GetMoviesPageUseCase
) {
    private val mutex = Mutex()

    private var loadedPages = 0
    private var totalPages = -1

    private var state = MoviesLoaderState.Empty
    private val _stateFlow = MutableStateFlow<LoadingState<MoviesLoaderState>>(
        LoadingState.Idle(state)
    )
    val stateFlow = _stateFlow.asStateFlow()
    private var favoriteMoviesIds = emptySet<Long>()

    suspend fun loadMore() = mutex.withLock {
        val page = loadedPages + 1
        if (totalPages >= 0 && page > totalPages) {
            Log.d(logTag(), "loadMore: there is no more pages requested page=$page pages:$totalPages")
            return
        }
        _stateFlow.value = LoadingState.Loading(state)
        val result = getMoviesPageUseCase(
            query = query,
            page = page,
        )
        when (result) {
            is QueryResult.Success -> onPageLoadSuccess(page, result.value)
            is QueryResult.Failure -> onPageLoadFailure(page, result.cause)
        }
    }

    private fun onPageLoadSuccess(page: Int, result: MovieList) {
        Log.d(logTag(), "Movie's page=$page loaded success")
        if (result.loadedPages != page) {
            Log.w(logTag(), "Movie's loaded pages has changed $page -> ${result.loadedPages}")
        }
        if (totalPages >= 0 && result.totalPages != totalPages) {
            Log.w(logTag(), "Movie's total pages has changed $totalPages -> ${result.totalPages}")
        }
        loadedPages = result.loadedPages
        totalPages = result.totalPages

        state = MoviesLoaderState(
            movies = state.movies + result.items,
            loadedPages = loadedPages,
            totalPages = totalPages,
        )
        _stateFlow.value = LoadingState.Idle(state)
    }

    private fun onPageLoadFailure(page: Int, cause: FailureCause) {
        Log.w(logTag(), "Movie's page=$page loaded failed with cause=$cause")

        _stateFlow.value = LoadingState.Failure(state, cause)
    }

    fun setFavoriteMoviesIds(moviesIds: Set<Long>) {
        var hasChanged = false
        favoriteMoviesIds = moviesIds
        val movies = state.movies.map {
            val isFavorite = moviesIds.contains(it.id)
            if (it.isFavorite != isFavorite) {
                hasChanged = true
                it.copy(isFavorite = isFavorite)
            } else it
        }
        if (hasChanged) {
            state = state.copy(movies = movies)
            _stateFlow.value = stateFlow.value.transformValue { state }
        }
    }

    companion object {
        fun create(
            query: MovieQuery,
            getMoviesPageUseCase: GetMoviesPageUseCase
        ) = MoviesLoader(query, getMoviesPageUseCase)
    }
}