package amaterek.movie.app.ui.moviedetails

import amaterek.movie.app.ui.common.view.LoadingStateView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieDetailsScreen(movieId: Long) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()

    LaunchedEffect(movieId) {
        viewModel.setMovieId(movieId = movieId, reload = false)
    }

    val moviesState = viewModel.movieFlow.collectAsState()
    val scrollState = rememberScrollState(0)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { viewModel.setMovieId(movieId = movieId, reload = true) }) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            moviesState.value.value?.let {
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
        moviesState.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp),
    )
}
