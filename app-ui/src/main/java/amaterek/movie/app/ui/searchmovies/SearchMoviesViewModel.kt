package amaterek.movie.app.ui.searchmovies

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.base.LoadingState
import amaterek.movie.base.moviesloader.MoviesLoader
import amaterek.movie.base.moviesloader.MoviesState
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

    private val debounceTimeMillis = 400L

    private val emptyMoviesState = MovieSearchState(
        phrase = "",
        moviesState = LoadingState.Idle(
            MoviesState(
                movies = emptyList(),
                hasMore = false,
            )
        ),
    )

    private val _moviesFlow = MutableStateFlow(emptyMoviesState)
    val moviesFlow = _moviesFlow.asStateFlow()

    private val phraseFlow = MutableStateFlow("")

    private var searchMoviesJob: Job? = null

    init {
        phraseFlow
            .debounce(debounceTimeMillis)
            .onEach(::handlePhraseChanged)
            .launchIn(viewModelScope)
    }

    fun searchMovieByPhrase(phrase: String) {
        _moviesFlow.value = MovieSearchState(
            phrase = phrase,
            moviesState = moviesFlow.value.moviesState
        )
        phraseFlow.value = phrase
    }

    private fun handlePhraseChanged(phrase: String) {
        if (phrase.isBlank()) {
            _moviesFlow.value = emptyMoviesState
        } else {
            searchMovies(createQueryForPhrase(phrase))
            _moviesFlow.value = MovieSearchState(
                phrase = phrase,
                moviesState = LoadingState.Loading(moviesFlow.value.moviesState.value)
            )
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
                    handleMoviesState(
                        state = it,
                        phrase = (query.type as MovieQuery.Type.ByPhrase).phrase
                    )
                }
            }
        }
    }

    private fun handleMoviesState(state: LoadingState<MoviesState>, phrase: String) {
        Log.v(logTag(), "handleMoviesState: set value=${state.value}")
        _moviesFlow.value = MovieSearchState(
            phrase = phrase,
            moviesState = state
        )
    }
}