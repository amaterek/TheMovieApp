package amaterek.movie.app.ui.moviedetails

import amaterek.base.log.Log
import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.app.ui.common.view.*
import amaterek.movie.domain.model.MovieDetails
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val movieDetailsDateFormat =
    SimpleDateFormat("yyyy/MM/dd")  // TODO Consider extracting to some common formatters

@Composable
internal fun MovieDetailsView(
    movieDetails: MovieDetails,
    onSetFavoriteClick: () -> Unit
) {
    Log.v("ComposeRender", "MovieDetailsView")

    val backDropImagePainter = rememberImagePainter(movieDetails.movie.backdropUrl) {
        crossfade(true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Box {
                Image(
                    painter = backDropImagePainter,
                    contentDescription = movieDetails.movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop,
                )
                ToggleFavoriteButton(
                    isFavorite = movieDetails.movie.isFavorite,
                    onClick = onSetFavoriteClick,
                    Modifier
                        .padding(defaultPadding)
                        .align(Alignment.TopEnd)
                )
            }
            MovieTitleView(
                movie = movieDetails.movie,
                dateFormat = movieDetailsDateFormat,
                fontSize = 24.sp,
                modifier = Modifier.padding(
                    top = defaultPadding,
                    start = defaultPadding * 2,
                    end = defaultPadding * 2
                ),
            )
            MovieRatingBar(
                modifier = Modifier.padding(
                    top = defaultPadding,
                    start = defaultPadding * 2,
                    end = defaultPadding * 2
                ),
                movieRating = movieDetails.movie.rating,
                size = 20.dp,
            )
            MovieGenresView(
                movieGenres = movieDetails.movie.genres,
                fontSize = 16.sp,
                modifier = Modifier.padding(
                    top = defaultPadding,
                    start = defaultPadding * 2,
                    end = defaultPadding * 2
                ),
            )
            MovieBudgetView(
                budget = movieDetails.budget,
                fontSize = 16.sp,
                modifier = Modifier.padding(
                    top = defaultPadding,
                    start = defaultPadding * 2,
                    end = defaultPadding * 2
                ),
            )
            MovieDescriptionView(
                movieDetails = movieDetails,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    top = defaultPadding,
                    start = defaultPadding * 2,
                    end = defaultPadding * 2
                ),
            )
            Box(
                modifier = Modifier.height(defaultPadding * 2),
            )
        }
    }
}

@Composable
private fun ToggleFavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteStarResId =
        if (isFavorite) R.drawable.favorite_star_enabled else R.drawable.favorite_star_disabled
    val favoriteStarDescriptionStringId =
        if (isFavorite) R.string.movie_details_favorite_description else R.string.movie_details_add_to_favorite_description
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        contentPadding = PaddingValues(defaultPadding)
    ) {
        Image(
            painter = painterResource(favoriteStarResId),
            contentDescription = stringResource(favoriteStarDescriptionStringId),
        )
    }
}