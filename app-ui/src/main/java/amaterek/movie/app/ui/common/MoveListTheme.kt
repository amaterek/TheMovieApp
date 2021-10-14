package amaterek.movie.app.ui.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ShimmerParams

// TODO Consider moving to theme
internal val defaultPadding = 6.dp
internal val ratingBarSize = 18.dp
internal val ratingBarPadding = 4.dp

@Composable
internal fun shimmerParams() = ShimmerParams(
    baseColor = MaterialTheme.colors.onSecondary,
    highlightColor = Color.Gray,
    dropOff = 0.8f,
    durationMillis = 800,
)