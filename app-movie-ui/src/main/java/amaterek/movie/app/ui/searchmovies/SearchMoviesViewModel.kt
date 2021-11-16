package amaterek.movie.app.ui.searchmovies

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.app.ui.common.model.toUiModel
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesLoaderState
import amaterek.movie.base.LoadingState
import amaterek.movie.base.transformValue
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.usecase.GetMoviesPageUseCase
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchMoviesViewModel @Inject constructor(
    private val getMoviesPageUseCase: GetMoviesPageUseCase
) : BaseViewModel() {

    private val _stateFlow = MutableStateFlow(EmptyMovieSearchState)
    val stateFlow = _stateFlow.asStateFlow()

    private val internalPhraseFlow = MutableStateFlow("")

    private var searchMoviesJob: Job? = null

    init {
        internalPhraseFlow
            .debounce(DebounceTimeMillis)
            .onEach(::handlePhraseChanged)
            .launchIn(viewModelScope)
    }

    fun searchMovieByPhrase(phrase: String) {
        _stateFlow.value = _stateFlow.value.copy(phrase = phrase)
        internalPhraseFlow.value = phrase
    }

    private fun handlePhraseChanged(phrase: String) {
        if (phrase.isBlank()) {
            _stateFlow.value = EmptyMovieSearchState
        } else {
            searchMovies(createQueryForPhrase(phrase))
        }
    }

    private fun createQueryForPhrase(phrase: String): MovieQuery {
        Log.v(logTag(), "createQueryForPhrase: phrase=$phrase")
        return MovieQuery(
            type = MovieQuery.Type.ByPhrase(phrase),
            sortBy = MovieQuery.SortBy.POPULARITY_DESCENDING,
            genres = emptySet(), // All
        )
    }

    private fun searchMovies(query: MovieQuery) {
        searchMoviesJob?.cancel()
        searchMoviesJob = viewModelScope.launch {
            with(MoviesLoader.create(query, getMoviesPageUseCase)) {
                loadMore()
                stateFlow.collect {
                    handleMoviesLoaderState(state = it)
                }
            }
        }
    }

    private fun handleMoviesLoaderState(state: LoadingState<MoviesLoaderState>) {
        Log.v(logTag(), "handleMoviesLoaderState: set value=${state.value}")
        _stateFlow.value = _stateFlow.value.copy(
            movies = state.value.movies.toUiModel(),
            loadingState = state.transformValue { }
        )
    }

    companion object {
        private const val DebounceTimeMillis = 400L

        private val EmptyMovieSearchState = SearchMoviesState(
            phrase = "",
            movies = emptyList(),
            loadingState = LoadingState.Idle(Unit),
        )
    }
}