package amaterek.movie.app.ui.common.view

import amaterek.base.log.Log
import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .aspectRatio(300f / 450f),
        shape = RoundedCornerShape(defaultPadding),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
    ) {
        Log.v("ComposeRender", "PosterImage")
        Image(
            painter = posterImagePainter,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}