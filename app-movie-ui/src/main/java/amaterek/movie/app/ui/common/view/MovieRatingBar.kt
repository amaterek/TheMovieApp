package amaterek.movie.app.ui.common.view

import amaterek.movie.domain.model.MovieRating
import amaterek.movie.theme.AppColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar

@Composable
fun MovieRatingBar(
    movieRating: MovieRating,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    RatingBar(
        value = movieRating.value * 5f / 100,
        size = size,
        isIndicator = true,
        activeColor = AppColor.ratingStarEnabled,
        inactiveColor = AppColor.ratingStarDisabled,
        onValueChange = {},
        onRatingChanged = {},
        modifier = modifier,
    )
}

@Preview
@Composable
fun MovieRatingBarPreview() {
    MovieRatingBar(
        movieRating = MovieRating(33),
        size = 20.dp
    )
}