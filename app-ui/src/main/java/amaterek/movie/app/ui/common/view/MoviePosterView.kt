package amaterek.movie.app.ui.common.view

import amaterek.base.log.Log
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
internal fun MoviePosterView(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val posterImagePainter = rememberImagePainter(movie.posterUrl) {
        crossfade(true)
    }

    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(defaultPadding),
            border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
            elevation = 0.dp,
        ) {
            Log.v("ComposeRender", "PosterImage")
            Image(
                painter = posterImagePainter,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(300f / 450f),
                contentScale = ContentScale.Crop,
            )
        }
    }
}