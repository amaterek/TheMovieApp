package amaterek.movie.base.compose.biswiperefresh

import amaterek.base.log.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.BiSwipeRefreshOverscrollIndicator(
    state: BiSwipeRefreshState,
    overscrollState: MutableState<Dp>,
    topIndicatorEnabled: Boolean = true,
    topOverscrollLoadingHeight: Dp = 0.dp,
    bottomIndicatorEnabled: Boolean = true,
    bottomOverscrollLoadingHeight: Dp = 0.dp,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {

    LaunchedEffect(state.isSwipeInProgress, state.isRefreshing) {
         when {
            !state.isSwipeInProgress && state.isRefreshing && state.offset > 0.dp -> {
                if (topOverscrollLoadingHeight >= 0.dp) {
                    Log.v("BiSwipeRefreshOverscrollIndicator", "loading animate top")
                    state.animateOffsetTo(topOverscrollLoadingHeight)
                }
            }
            !state.isSwipeInProgress && state.isRefreshing && state.offset < 0.dp -> {
                if (bottomOverscrollLoadingHeight >= 0.dp) {
                    Log.v("BiSwipeRefreshOverscrollIndicator", "loading animate bottom")
                    state.animateOffsetTo(-bottomOverscrollLoadingHeight)
                }
            }
            !state.isSwipeInProgress && !state.isRefreshing -> {
                Log.v("BiSwipeRefreshOverscrollIndicator", "animate to 0.dp")
                state.animateOffsetTo(0.dp)
            }
        }
    }

    LaunchedEffect(state.offset) {
        if (topIndicatorEnabled && state.offset > 0.dp || bottomIndicatorEnabled && state.offset < 0.dp) {
            overscrollState.value = state.offset
        } else {
            overscrollState.value = 0.dp
        }
    }

    content?.let {
        if (overscrollState.value != 0.dp) {
            Box(
                modifier = Modifier
                    .padding(
                        top = if (overscrollState.value > 0.dp) topPadding else 0.dp,
                        bottom = if (overscrollState.value < 0.dp) bottomPadding else 0.dp,
                    )
                    .height(abs(overscrollState.value))
                    .align(if (overscrollState.value > 0.dp) Alignment.TopCenter else Alignment.BottomCenter),
            ) {
                content()
            }
        }
    }
}