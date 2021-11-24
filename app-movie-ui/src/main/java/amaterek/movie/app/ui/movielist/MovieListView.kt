package amaterek.movie.app.ui.movielist

import amaterek.base.log.Log
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.base.LoadingState
import amaterek.movie.theme.LoadingIndicatorAlignment
import amaterek.movie.theme.LoadingIndicatorColor
import amaterek.movie.theme.LoadingIndicatorHeight
import amaterek.movie.theme.LoadingIndicatorPadding
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

internal val SwipeRefreshTrigger = 120.dp

@Composable
internal fun MovieListView(
    movies: List<UiMovie>,
    loadingState: LoadingState<*>,
    overscrollState: MutableState<Dp>,
    modifier: Modifier = Modifier,
    onNeedMoreItems: () -> Unit,
    onMovieClick: (UiMovie) -> Unit,
) {
    Log.v("ComposeRender", "MovieListView")

    val itemsInRow = itemsInRow(LocalConfiguration.current.orientation)
    val rows = listRows(movies.size, itemsInRow)
    val missingItemsToFullRow = rows * itemsInRow - movies.size
    val lazyListState = rememberLazyListState()
    val rowsState = rememberSaveable { mutableStateOf(0) } // state is needed for LaunchedEffect
    rowsState.value = rows

    LaunchedEffect(rowsState, lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .map {
                val lastVisibleRow =
                    if (it.visibleItemsInfo.isNotEmpty()) it.visibleItemsInfo.last().index + 1 else 0
                lastVisibleRow + 2 > rowsState.value
            }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    Log.d("MovieListView", "LaunchedEffect: onNeedMoreItems")
                    onNeedMoreItems()
                }
            }
    }

    val lastOverscrollState = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    LaunchedEffect(overscrollState.value) {
        val delta = lastOverscrollState.value - overscrollState.value
        lastOverscrollState.value = overscrollState.value
        if (overscrollState.value < 0.dp && delta > 0.dp) {
            lazyListState.dispatchRawDelta(with(density) { delta.toPx() })
        }
    }

    LazyVerticalGrid(
        modifier = modifier.offset(x = 0.dp, y = maxOf(overscrollState.value, 0.dp)),
        state = lazyListState,
        contentPadding = PaddingValues(defaultPadding),
        cells = GridCells.Fixed(itemsInRow),
    ) {
        items(movies.size) {
            MovieItemView(
                movie = movies[it],
                onMovieClick = onMovieClick,
            )
        }

        if (missingItemsToFullRow > 0) {
            item(span = { GridItemSpan(missingItemsToFullRow) }) { Box(modifier = Modifier.fillMaxSize()) }
        }

        loadingBottomItem(
            loadingState = loadingState,
            overscroll = overscrollState.value,
            rows = rows,
            itemsInRow = itemsInRow,
        )
    }
}

private fun LazyGridScope.loadingBottomItem(
    loadingState: LoadingState<*>,
    overscroll: Dp,
    rows: Int,
    itemsInRow: Int,
) {
    val height = when {
        rows <= 0 -> 0.dp
        loadingState is LoadingState.Loading -> maxOf(80.dp, -overscroll)
        overscroll < 0.dp -> -overscroll
        else -> 0.dp
    }

    if (height <= 0.dp) return

    item(span = { GridItemSpan(itemsInRow) }) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height)
        ) {
            LoadingIndicator(
                height = height,
                loadingState = loadingState
            )
        }
    }
}

@Composable
private fun BoxScope.LoadingIndicator(height: Dp, loadingState: LoadingState<*>) {
    val size = minOf(height, LoadingIndicatorHeight)
    when (loadingState) {
        is LoadingState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(LoadingIndicatorPadding)
                    .size(size)
                    .align(LoadingIndicatorAlignment),
                color = LoadingIndicatorColor
            )
        }
        else -> Unit
    }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun itemsInRow(orientation: Int) =
    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

@Suppress("NOTHING_TO_INLINE")
private inline fun listRows(itemsCount: Int, itemsInRow: Int) =
    if (itemsCount % itemsInRow == 0) itemsCount / itemsInRow else itemsCount / itemsInRow + 1
