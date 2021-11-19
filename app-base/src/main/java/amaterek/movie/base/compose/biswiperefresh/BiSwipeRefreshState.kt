package amaterek.movie.base.compose.biswiperefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberBiSwipeRefreshState(
    isRefreshing: Boolean,
): BiSwipeRefreshState {
    return remember { BiSwipeRefreshState(isRefreshing = isRefreshing) }
}

@Stable
class BiSwipeRefreshState(
    isRefreshing: Boolean,
) {
    private val offsetAnimatable = Animatable(0f)
    private val mutatorMutex = MutatorMutex()

    var isRefreshing: Boolean by mutableStateOf(isRefreshing)

    var offset: Dp by mutableStateOf(0.dp)
        internal set

    var isSwipeInProgress: Boolean by mutableStateOf(false)
        internal set

    suspend fun animateOffsetTo(finalOffset: Dp) {
        offsetAnimatable.snapTo(offset.value)
        mutatorMutex.mutate {
            if (offset != finalOffset) {
                offsetAnimatable.animateTo(finalOffset.value) {
                    offset = value.dp
                }
            }
        }
    }
}