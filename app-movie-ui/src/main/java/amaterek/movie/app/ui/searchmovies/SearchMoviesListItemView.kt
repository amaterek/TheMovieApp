package amaterek.movie.app.ui.searchmovies

import amaterek.base.log.Log
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.app.ui.common.view.MovieGenresView
import amaterek.movie.app.ui.common.view.MovieRatingBar
import amaterek.movie.app.ui.common.view.MovieTitleView
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val movieDateFormat =
    SimpleDateFormat("yyyy/MM") // TODO Consider extracting to some common formatters

@Composable
internal fun SearchMoviesListItemView(
    movie: UiMovie,
    modifier: Modifier = Modifier,
) {
    Log.v("ComposeRender", "MovieSearchItemView")

    Column(
        modifier = modifier,
    ) {
        MovieTitleView(
            movie = movie,
            dateFormat = movieDateFormat,
        )
        MovieRatingBar(
            movieRating = movie.rating,
            size = 14.dp,
        )
        MovieGenresView(
            movieGenres = movie.genres,
            fontSize = 12.sp
        )
    }
}