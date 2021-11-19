package amaterek.movie.base.compose.biswiperefresh

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BiSwipeRefreshIndicator(
    state: BiSwipeRefreshState,
    refreshTrigger: Dp,
    showSwipeIndicator: Boolean = true,
    showLoadingIndicator: Boolean = true,
    modifier: Modifier = Modifier,
) {
    if (state.offset == 0.dp) return

    when {
        showSwipeIndicator && state.isSwipeInProgress -> {
            val progress = state.offset / refreshTrigger
            CircularProgressIndicator(
                progress = progress,
                modifier = modifier,
                color = MaterialTheme.colors.secondary
            )
        }
        showLoadingIndicator && state.isRefreshing -> {
            CircularProgressIndicator(
                modifier = modifier,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}