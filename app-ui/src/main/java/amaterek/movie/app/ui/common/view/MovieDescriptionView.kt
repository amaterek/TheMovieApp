package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.R
import amaterek.movie.domain.model.MovieDetails
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
internal fun MovieDescriptionView(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.movie_details_description),
            fontSize = fontSize,
            fontWeight = fontWeight,
        )
        Text(
            text = movieDetails.description,
        )
    }
}