package amaterek.movie.app.ui.searchmovies

import amaterek.base.log.Log
import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.app.ui.common.view.LoadingStateIcon
import amaterek.movie.base.LoadingState
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun SearchMoviesView(
    moviesSearchState: MovieSearchState,
    modifier: Modifier = Modifier,
    onSearchPhraseChange: (String) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    Log.v("ComposeRender", "SearchMoviesView")

    val isError = moviesSearchState.moviesState is LoadingState.Failure

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        val customTextSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colors.secondary,
            backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
        )

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = defaultPadding * 2,
                        end = defaultPadding * 2,
                        top = defaultPadding * 2,
                        bottom = defaultPadding
                    ),
                maxLines = 1,
                value = moviesSearchState.phrase,
                isError = isError,
                onValueChange = {
                    onSearchPhraseChange(it)
                },
                placeholder = {
                    Text(text = stringResource(R.string.movie_list_search_hint), color = Color.Gray)
                },
                trailingIcon = {
                    LoadingStateIcon(
                        imageVector = Icons.Filled.Search,
                        loadingState = moviesSearchState.moviesState,
                    )
                },
                colors = outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    errorCursorColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedBorderColor = MaterialTheme.colors.secondary,
                ),
            )
        }

        val movies = moviesSearchState.moviesState.value.movies
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(movies.size) {
                val movie = movies[it]
                MovieSearchItemView(
                    movie = movie,
                    modifier = Modifier
                        .clickable { onMovieClick(movie) }
                        .fillMaxWidth()
                        .padding(
                            start = defaultPadding * 3,
                            end = defaultPadding * 3,
                            top = defaultPadding,
                            bottom = defaultPadding
                        ),
                )
            }
        }
    }
}