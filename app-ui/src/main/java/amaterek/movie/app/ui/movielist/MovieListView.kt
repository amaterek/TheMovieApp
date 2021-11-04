package amaterek.movie.app.ui.movielist

import amaterek.base.log.Log
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.base.moviesloader.MoviesState
import amaterek.movie.domain.model.Movie
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
internal fun MovieListView(
    moviesState: MoviesState,
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    Log.v("ComposeRender", "MovieListView")

    val itemsInRow = when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }
    val itemsCount = moviesState.movies.size
    val listSize =
        if (itemsCount % itemsInRow == 0) itemsCount / itemsInRow else itemsCount / itemsInRow + 1
    val listState = rememberLazyListState()

    LaunchedEffect(listState, listSize) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map {
                val lastVisibleRow = if (it.isNotEmpty()) it.last().index + 1 else 0
                lastVisibleRow + 2 > listSize
            }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    Log.i("MovieListView", "request more items")
                    onLoadMore()
                }
            }
    }

    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(defaultPadding),
        cells = GridCells.Fixed(itemsInRow),
        state = listState
    ) {
        items(itemsCount) {
            MovieItemView(
                movie = moviesState.movies[it],
                onMovieClick = onMovieClick,
            )
        }
        if (moviesState.hasMore) {
            // Placeholder for next items but id doesn't work if itemsCount % itemsInRow != 0
            // Need to wait for google or implement using LazyColumn
            // TODO Add footer/placeholder
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                )
            }
        }
    }
}
