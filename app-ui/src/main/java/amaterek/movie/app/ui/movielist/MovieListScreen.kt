package amaterek.movie.app.ui.movielist

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.view.LoadingStateView
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MovieListScreen(
    onMovieClick: (Movie) -> Unit,
) {

    val viewModel = hiltViewModel<MovieListViewModel>()

    val moviesState = viewModel.moviesFlow.collectAsState()

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = rememberCollapsingToolbarScaffoldState(),
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    elevation = 1.dp
                )
            }
        ) {
            MovieListView(
                moviesState = moviesState.value.value,
                onLoadMore = { viewModel.requestLoadMore() },
                onMovieClick = onMovieClick,
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
