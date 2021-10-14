package amaterek.movie.app.ui.searchmovies

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SearchMovieDialog(
    onMovieClick: (Movie) -> Unit,
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (showDialog) {
        val viewModel = hiltViewModel<SearchMoviesViewModel>()

        val moviesSearchState = viewModel.moviesFlow.collectAsState()

        Dialog(
            onDismissRequest = onDismissRequest,
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 48.dp, bottom = 48.dp)
                    .fillMaxSize(),
                border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
                elevation = 0.dp,
                shape = RoundedCornerShape(defaultPadding * 2),
            ) {
                SearchMoviesView(
                    moviesSearchState = moviesSearchState.value,
                    onMovieClick = onMovieClick,
                    onSearchPhraseChange = { viewModel.searchMovieByPhrase(it) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}