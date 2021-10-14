package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.common.defaultPadding
import amaterek.movie.app.ui.common.shimmerParams
import amaterek.movie.domain.model.Movie
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage

@Composable
internal fun MoviePosterView(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            CoilImage(
                imageModel = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(300f / 450f),
                contentScale = ContentScale.Crop,
                shimmerParams = shimmerParams(),
            )
        }
    }
}