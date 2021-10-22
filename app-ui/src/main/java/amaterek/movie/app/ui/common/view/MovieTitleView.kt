package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.R
import amaterek.movie.base.date.dateOf
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieRating
import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
private fun Date?.formatDate(dateFormat: DateFormat): String =
    this?.let { dateFormat.format(this) } ?: stringResource(R.string.movie_unknown_date)

@Composable
internal fun MovieTitleView(
    movie: Movie,
    dateFormat: DateFormat,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Text(
        text = "${movie.title} (${movie.releaseDate.formatDate(dateFormat)})",
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
    )
}

@SuppressLint("SimpleDateFormat")
@Preview
@Composable
internal fun MovieTitleViewPreview() {
    MovieTitleView(
        movie = Movie(
            id = 1,
            title = "Matrix",
            rating = MovieRating(0),
            genres = emptyList(),
            releaseDate =  dateOf(2011, 7, 21),
            posterUrl = "",
            backdropUrl = "",
            isFavorite = true,
        ),
        dateFormat = SimpleDateFormat("yyyy-MM-dd"),
    )
}