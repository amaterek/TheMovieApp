package amaterek.movie.app.ui.common.view

import amaterek.movie.base.LoadingState
import amaterek.movie.domain.common.FailureCause
import amaterek.movie.theme.LoadingIndicatorColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun LoadingStateView(
    loadingState: LoadingState<Any?>,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        when (loadingState) {
            is LoadingState.Idle<*> -> Unit
            is LoadingState.Loading<*> -> LinearProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = LoadingIndicatorColor
            )
            is LoadingState.Failure<*> -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.error),
                )
            }
        }
    }
}

@Preview
@Composable
internal fun LoadingStateViewPreviewError() {
    LoadingStateView(
        loadingState = LoadingState.Failure<Any?>(null, FailureCause.Error),
        modifier = Modifier.fillMaxSize()
    )
}