package amaterek.movie.app.ui.moviedetails

import amaterek.movie.app.ui.common.view.LoadingStateView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieDetailsScreen(movieId: Long) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    viewModel.setMovieId(movieId)

    val moviesState = viewModel.movieFlow.collectAsState()

    val movieDetails = moviesState.value.value

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (movieDetails != null) {
            MovieDetailsView(
                movieDetails = movieDetails,
                onSetFavoriteClick = {
                    viewModel.toggleFavorite()
                }
            )
        }

        LoadingStateView(
            moviesState.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
        )
    }
}
