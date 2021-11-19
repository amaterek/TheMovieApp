package amaterek.movie.base.compose.biswiperefresh

import amaterek.base.log.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

private class BiSwipeRefreshNestedScrollConnection(
    private val state: BiSwipeRefreshState,
    private val density: Density,
    private val onRefresh: () -> Unit,
) : NestedScrollConnection {
    var topRefreshTriggerDistance: Dp = 0.dp
    var bottomRefreshTriggerDistance: Dp = 0.dp

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource,
    ): Offset {
        val isDragging = (source == NestedScrollSource.Drag)
        return when {
            state.isRefreshing -> {
                if (abs(state.offset) > 0.dp) Offset(0f, available.y) else Offset.Zero
            }
            !isDragging -> Offset.Zero
            isDragging && (available.y > 0f) -> onPreDrag(available.y)
            isDragging && (available.y < 0f) -> onPreDrag(available.y)
            else -> Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset {
        val isDragging = (source == NestedScrollSource.Drag)
        return when {
            state.isRefreshing -> Offset.Zero
            !isDragging -> Offset.Zero
            isDragging && (available.y > 0f) -> onPostDrag(available.y)
            isDragging && (available.y < 0f) -> onPostDrag(available.y)
            else -> Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        val offset = state.offset
        val isNotRefreshing = !state.isRefreshing
        when {
            isNotRefreshing && (topRefreshTriggerDistance > 0.dp) && (offset >= topRefreshTriggerDistance) -> {
                Log.v("BiSwipeRefresh", "onRefresh() top")
                onRefresh()
            }
            isNotRefreshing && (bottomRefreshTriggerDistance > 0.dp) && (offset <= -bottomRefreshTriggerDistance) -> {
                Log.v("BiSwipeRefresh", "onRefresh() bottom")
                onRefresh()
            }
        }
        state.isSwipeInProgress = false
        return Velocity.Zero
    }

    private fun onPreDrag(availableY: Float): Offset {
        if (state.isSwipeInProgress) {
            with(density) {
                val nextOffset = state.offset + availableY.toDp()
                if (nextOffset.value * state.offset.value < 0) {
                    state.isSwipeInProgress = false
                    state.offset = 0.dp
                } else {
                    state.offset += availableY.toDp()
                    return Offset(x = 0f, y = availableY)
                }
            }
        }
        return Offset.Zero
    }

    private fun onPostDrag(availableY: Float): Offset {
        state.isSwipeInProgress = state.isSwipeInProgress || availableY != 0f
        state.offset += with(density) { availableY.toDp() }
        return Offset.Zero
    }
}

@Composable
fun BiSwipeRefresh(
    state: BiSwipeRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    topRefreshTriggerDistance: Dp,
    bottomRefreshTriggerDistance: Dp,
    indicator: @Composable BoxScope.(state: BiSwipeRefreshState, topRefreshTrigger: Dp, bottomRefreshTrigger: Dp) -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)

    val nestedScrollConnection = remember(state, density, coroutineScope) {
        BiSwipeRefreshNestedScrollConnection(state, density) {
            updatedOnRefresh.value()
        }
    }.also {
        it.topRefreshTriggerDistance = topRefreshTriggerDistance
        it.bottomRefreshTriggerDistance = bottomRefreshTriggerDistance
    }

    Box(
        modifier.nestedScroll(connection = nestedScrollConnection)
    ) {
        content()

        indicator(state, topRefreshTriggerDistance, bottomRefreshTriggerDistance)
    }
}