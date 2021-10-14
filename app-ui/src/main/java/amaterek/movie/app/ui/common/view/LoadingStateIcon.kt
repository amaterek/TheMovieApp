package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.R
import amaterek.movie.base.LoadingState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingStateIcon(
    imageVector: ImageVector,
    loadingState: LoadingState<Any?>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (loadingState) {
            is LoadingState.Idle<*> -> {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                )
            }
            is LoadingState.Loading<*> -> {
                val size = minOf(imageVector.defaultWidth, imageVector.defaultHeight)
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = 2.dp,
                    modifier = modifier
                        .width(size)
                        .height(size),
                )
            }
            is LoadingState.Failure<*> -> {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                )
            }
        }
    }
}