package amaterek.movie.app.ui.login

import amaterek.base.log.Log
import amaterek.movie.app.ui.login.view.LoginView
import amaterek.movie.app.ui.login.LoginViewModel.SideEffect.UserLoggedIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(
    onUserLoggedIn: () -> Unit,
) {
    Log.v("ComposeRender", "LoginScreen")

    val viewModel: LoginViewModel = hiltViewModel()

    LaunchedEffect(viewModel) {
        viewModel.sideEffect
            .onEach {
                when (it) {
                    UserLoggedIn -> onUserLoggedIn()
                }
            }
            .launchIn(this)
    }

    LoginView(
        viewStateFlow = viewModel.state,
        usernameInputStateFlow = viewModel.usernameInputStateFlow,
        passwordInputStateFlow = viewModel.passwordInputStateFlow,
        onUsernameValueChanged = { viewModel.setUsername(it) },
        onPasswordValueChanged = { viewModel.setPassword(it) },
        onSubmit = { viewModel.submit() },
        onTryAgain = { viewModel.tryAgain() },
    )
}
