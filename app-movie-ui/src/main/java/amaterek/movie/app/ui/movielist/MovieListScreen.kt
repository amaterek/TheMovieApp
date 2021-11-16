package amaterek.movie.app.ui.movielist

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.app.ui.common.view.LoadingStateView
import amaterek.movie.app.ui.searchmovies.SearchMoviesDialog
import amaterek.movie.base.LoadingState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MovieListScreen(
    onMovieClick: (UiMovie) -> Unit,
) {

    val viewModel = hiltViewModel<MovieListViewModel>()

    val movieListState = viewModel.stateFlow.collectAsState()

    val showDialog = rememberSaveable { mutableStateOf(false) }

    val swipeRefreshState = rememberSwipeRefreshState(movieListState is LoadingState.Loading<*>)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.requestLoadMore() },
    ) {
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
                        elevation = 1.dp,
                        actions = {
                            IconButton(
                                onClick = { showDialog.value = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = stringResource(R.string.movie_list_search_content_description),
                                )
                            }
                        },
                    )
                }
            ) {
                MovieListView(
                    movies = movieListState.value.movies,
                    modifier = Modifier.fillMaxSize(),
                    onLoadMore = { viewModel.requestLoadMore() },
                    onMovieClick = onMovieClick,
                )
            }
        }
    }

    LoadingStateView(
        movieListState.value.loadingState,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp),
    )

    SearchMoviesDialog(
        onMovieClick = onMovieClick,
        showDialog = showDialog.value,
        onDismissRequest = { showDialog.value = false }
    )
}
