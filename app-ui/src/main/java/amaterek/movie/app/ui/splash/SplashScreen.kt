package amaterek.movie.app.ui.splash

import amaterek.movie.app.ui.splash.SplashViewModel.Event.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(viewModel) {
        viewModel.eventFlow
            .onEach {
                when (it) {
                    SplashFinished -> onSplashFinished()
                }
            }
            .launchIn(this)
    }

    SplashView(
        modifier = Modifier.fillMaxSize(),
        onRequestFinish = { viewModel.requestFinish() },
    )
}
