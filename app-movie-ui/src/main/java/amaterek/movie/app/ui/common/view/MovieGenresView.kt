package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.common.getMovieGenreStringId
import amaterek.movie.domain.model.MovieGenre
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
internal fun MovieGenresView(
    movieGenres: List<MovieGenre>,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    val displayMovieGenres = movieGenres
        .map { stringResource(getMovieGenreStringId(it)) }
        .sorted()
    Text(
        text = displayMovieGenres.joinToString(separator = "/"),
        fontWeight = fontWeight,
        fontSize = fontSize,
        modifier = modifier,
    )
}

@Preview
@Composable
internal fun MovieGenresView() {
    MovieGenresView(
        movieGenres = listOf(MovieGenre.SCIENCE_FICTION, MovieGenre.ACTION)
    )
}