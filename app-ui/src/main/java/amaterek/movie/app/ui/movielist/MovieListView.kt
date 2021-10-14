package amaterek.movie.app.ui.movielist

import amaterek.base.log.Log
import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.base.moviesloader.MoviesState
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
internal fun MovieListView(
    moviesState: MoviesState,
    onLoadMore: () -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    Log.v("ComposeRender", "MovieListView")

    val itemCount = if (moviesState.hasMore) {
        moviesState.movies.size + 1
    } else {
        moviesState.movies.size
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(defaultPadding),
        cells = GridCells.Adaptive(minSize = 190.dp),
    ) {
        items(itemCount) {
            if (it < moviesState.movies.size) {
                MovieItemView(
                    movie = moviesState.movies[it],
                    onMovieClick = onMovieClick,
                )
            } else {
                // TODO Implement better way to load more movies ex. SwipeRefresh
                Text(
                    text = stringResource(R.string.load_more_movies_button),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = defaultPadding)
                        .clickable(onClick = onLoadMore)
                )
            }
        }
    }
}
