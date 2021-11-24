package amaterek.movie.app.ui.movielist

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.app.ui.common.view.LoadingStateView
import amaterek.movie.app.ui.searchmovies.SearchMoviesDialog
import amaterek.movie.base.LoadingState
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefresh
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefreshIndicator
import amaterek.movie.base.compose.biswiperefresh.BiSwipeRefreshOverscrollIndicator
import amaterek.movie.base.compose.biswiperefresh.rememberBiSwipeRefreshState
import amaterek.movie.theme.AppBarHeight
import amaterek.movie.theme.LoadingIndicatorAlignment
import amaterek.movie.theme.LoadingIndicatorHeight
import amaterek.movie.theme.LoadingIndicatorPadding
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MovieListScreen(
    onMovieClick: (UiMovie) -> Unit,
) {

    val viewModel = hiltViewModel<MovieListViewModel>()
    val viewModelState = viewModel.stateFlow.collectAsState()
    val showDialogState = rememberSaveable { mutableStateOf(false) }

    val swipeRefreshState = rememberBiSwipeRefreshState(false)
    val overscrollState = remember { mutableStateOf(0.dp) }

    LaunchedEffect(viewModelState) {
        snapshotFlow { viewModelState.value.loadingState }
            .onEach {
                when (it) {
                    is LoadingState.Loading -> swipeRefreshState.isRefreshing = true
                    is LoadingState.Idle -> swipeRefreshState.isRefreshing = false
                    is LoadingState.Failure -> swipeRefreshState.isRefreshing = false
                }
            }.launchIn(this)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BiSwipeRefresh(
            state = swipeRefreshState,
            topRefreshTriggerDistance = SwipeRefreshTrigger,
            bottomRefreshTriggerDistance = SwipeRefreshTrigger,
            onRefresh = { viewModel.requestLoadMore() },
            indicator = { state, _, _ ->
                BiSwipeRefreshOverscrollIndicator(
                    state = state,
                    overscrollState = overscrollState,
                    bottomOverscrollLoadingHeight = LoadingIndicatorPadding * 2 + LoadingIndicatorHeight,
                    topPadding = AppBarHeight,
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
            MovieListToolBar(
                showDialogState = showDialogState
            ) {
                MovieListView(
                    movies = viewModelState.value.movies,
                    loadingState = viewModelState.value.loadingState,
                    overscrollState = overscrollState,
                    modifier = Modifier.fillMaxSize(),
                    onNeedMoreItems = { viewModel.requestLoadMore() },
                    onMovieClick = onMovieClick,
                )
            }
        }
    }

    LoadingStateView(
        viewModelState.value.loadingState,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp),
    )

    SearchMoviesDialog(
        onMovieClick = onMovieClick,
        showDialog = showDialogState.value,
        onDismissRequest = { showDialogState.value = false }
    )
}

@Composable
private fun MovieListToolBar(
    showDialogState: MutableState<Boolean>,
    body: @Composable () -> Unit,
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
                        onClick = { showDialogState.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.movie_list_search_content_description),
                        )
                    }
                },
            )
        },
        body = body
    )
}
