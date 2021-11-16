package amaterek.movie.app.ui.movielist

import amaterek.base.log.Log
import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.app.ui.common.ratingBarPadding
import amaterek.movie.app.ui.common.ratingBarSize
import amaterek.movie.app.ui.common.view.MovieGenresView
import amaterek.movie.app.ui.common.view.MoviePosterView
import amaterek.movie.app.ui.common.view.MovieRatingBar
import amaterek.movie.app.ui.common.view.MovieTitleView
import amaterek.movie.domain.model.MovieGenre
import amaterek.movie.domain.model.MovieRating
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val movieDateFormat =
    SimpleDateFormat("yyyy") // TODO Consider extracting to some common formatters

@Composable
internal fun MovieItemView(
    movie: UiMovie,
    modifier: Modifier = Modifier,
    onMovieClick: (UiMovie) -> Unit,
) {
    Log.v("ComposeRender", "MovieItemView: ${movie.id}")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            MoviePosterView(
                movie = movie,
                onClick = { onMovieClick(movie) },
                modifier = Modifier.padding(defaultPadding)
            )
            MovieRatingBar(
                movieRating = movie.rating,
                size = ratingBarSize,
                modifier = Modifier
                    .padding(defaultPadding + ratingBarPadding)
                    .align(Alignment.BottomStart),
            )
            if (movie.isFavorite) {
                Image(
                    painter = painterResource(R.drawable.favorite_star_enabled),
                    contentDescription = stringResource(R.string.movie_list_favorite_description),
                    modifier = Modifier
                        .padding(defaultPadding * 2)
                        .align(Alignment.TopEnd),
                )
            }
        }
        MovieTitleView(
            movie = movie,
            dateFormat = movieDateFormat,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = defaultPadding),
        )
        MovieGenresView(
            movieGenres = movie.genres,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = defaultPadding, bottom = defaultPadding * 2),
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun MovieItemViewPreview() {
    MovieItemView(
        movie = UiMovie(
            id = 1,
            title = "Matrix",
            rating = MovieRating(90),
            genres = listOf(MovieGenre.ACTION, MovieGenre.SCIENCE_FICTION),
            releaseDate = null,
            posterUrl = "",
            backdropUrl = "",
            isFavorite = true,
        ),
        onMovieClick = {}
    )
}
