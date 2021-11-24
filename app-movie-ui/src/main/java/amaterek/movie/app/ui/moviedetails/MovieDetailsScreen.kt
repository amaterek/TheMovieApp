package amaterek.movie.app.ui.moviedetails

import amaterek.movie.app.ui.common.view.LoadingStateView
import amaterek.movie.app.ui.movielist.SwipeRefreshTrigger
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefresh
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefreshIndicator
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefreshOverscrollIndicator
import amaterek.movie.base.compose.biswiperefresh.rememberBiSwipeRefreshState
import amaterek.movie.theme.LoadingIndicatorAlignment
import amaterek.movie.theme.LoadingIndicatorHeight
import amaterek.movie.theme.LoadingIndicatorPadding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieDetailsScreen(movieId: Long) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()

    LaunchedEffect(movieId) {
        viewModel.setMovieId(movieId = movieId, reload = false)
    }

    val moviesLoaderState = viewModel.movieFlow.collectAsState()
    val scrollState = rememberScrollState(0)
    val overscrollState = remember { mutableStateOf(0.dp) }

    BiSwipeRefresh(
        state = rememberBiSwipeRefreshState(isRefreshing = false),
        onRefresh = { viewModel.setMovieId(movieId = movieId, reload = true) },
        topRefreshTriggerDistance = SwipeRefreshTrigger,
        bottomRefreshTriggerDistance = 0.dp,
        indicator = { state, _, _ ->
            BiSwipeRefreshOverscrollIndicator(
                state = state,
                overscrollState = overscrollState,
                bottomIndicatorEnabled = false,
            ) {
                BiSwipeRefreshIndicator(
                    state = state,
                    refreshTrigger = SwipeRefreshTrigger,
                    showLoadingIndicator = false,
                    modifier = Modifier
                        .padding(LoadingIndicatorPadding)
                        .size(LoadingIndicatorHeight)
                        .align(LoadingIndicatorAlignment),
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .offset(x = 0.dp, y = maxOf(overscrollState.value, 0.dp))
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            moviesLoaderState.value.value?.let {
                MovieDetailsView(
                    movieDetails = it,
                    modifier = Modifier.fillMaxSize(),
                    onSetFavoriteClick = {
                        viewModel.toggleFavorite()
                    }
                )
            }
        }
    }

    LoadingStateView(
        moviesLoaderState.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp),
    )
}
