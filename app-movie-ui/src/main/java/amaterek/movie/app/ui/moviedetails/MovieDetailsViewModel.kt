package amaterek.movie.app.ui.moviedetails

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.app.ui.common.model.UiMovieDetails
import amaterek.movie.app.ui.common.model.toUiModel
import amaterek.movie.base.LoadingState
import amaterek.movie.base.transformValue
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieDetails
import amaterek.movie.domain.usecase.GetMovieDetailsUseCase
import amaterek.movie.domain.usecase.ObserveFavoriteMoviesUseCase
import amaterek.movie.domain.usecase.SetFavouriteMovieUseCase
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    observeFavoriteMoviesUseCase: ObserveFavoriteMoviesUseCase,
) : BaseViewModel() {

    private val _movieFlow = MutableStateFlow<LoadingState<UiMovieDetails?>>(
        LoadingState.Idle(null)
    )
    val movieFlow = _movieFlow.asStateFlow()

    init {
        observeFavoriteMoviesUseCase()
            .onEach(::handleFavoriteMoviesIds)
            .launchIn(viewModelScope)
    }

    fun setMovieId(movieId: Long, reload: Boolean) {
        Log.v(logTag(), "setMovieId(): setMovieId=$movieId")
        if (!reload && (movieFlow.value.value?.movie?.id == movieId)) return
        _movieFlow.value = LoadingState.Loading(movieFlow.value.value)
        viewModelScope.launch {
            when (val result = getMovieDetailsUseCase(movieId)) {
                is QueryResult.Success -> handleMovieDetails(result.value)
                is QueryResult.Failure -> handleMovieDetailsError(result.cause)
            }
        }
    }

    private fun handleMovieDetails(movieDetails: MovieDetails) {
        _movieFlow.value = LoadingState.Idle(movieDetails.toUiModel())
    }

    private fun handleMovieDetailsError(failureCause: FailureCause) {
        _movieFlow.value = LoadingState.Failure(movieFlow.value.value, failureCause)
    }

    fun toggleFavorite() {
        Log.v(logTag(), "toggleFavorite")
        movieFlow.value.value?.let {
            viewModelScope.launch {
                setFavouriteMovieUseCase(it.movie.id, !it.movie.isFavorite)
            }
        }
    }

    private fun handleFavoriteMoviesIds(favoriteMoviesIds: Set<Long>) {
        movieFlow.value.value?.let {
            val isFavorite = favoriteMoviesIds.contains(it.movie.id)
            if (isFavorite != it.movie.isFavorite) {
                _movieFlow.value = movieFlow.value.transformValue {
                    it.copy(movie = it.movie.copy(isFavorite = isFavorite))
                }
            }
        }
    }
}