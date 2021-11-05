package amaterek.movie.app.ui.login.view

import amaterek.base.log.Log
import amaterek.movie.app.ui.login.LoginViewModel
import amaterek.movie.app.ui.login.input.PasswordInputStateFlow
import amaterek.movie.app.ui.login.input.UsernameInputStateFlow
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
internal fun LoginView(
    viewStateFlow: StateFlow<LoginViewModel.State>,
    usernameInputStateFlow: UsernameInputStateFlow,
    passwordInputStateFlow: PasswordInputStateFlow,
    onUsernameValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onTryAgain: () -> Unit,
) {
    Log.v("ComposeRender", "LoginScreenView")

    val targetState = viewStateFlow.collectAsState()
    Crossfade(targetState = targetState.value) { state ->
        when (state) {
            LoginViewModel.State.Form -> LoginFormView(
                usernameInputStateFlow = usernameInputStateFlow,
                passwordInputStateFlow = passwordInputStateFlow,
                onUsernameValueChanged = onUsernameValueChanged,
                onPasswordValueChanged = onPasswordValueChanged,
                onSubmit = onSubmit,
            )
            LoginViewModel.State.Progress -> LoginProgressView()
            LoginViewModel.State.Success -> LoginSuccessView()
            LoginViewModel.State.Error -> LoginErrorView(
                onTryAgain = onTryAgain,
            )
        }
    }
}

@Composable
@Preview
private fun LoginViewPreviewForm() {
    LoginView(
        viewStateFlow = MutableStateFlow(LoginViewModel.State.Form),
        usernameInputStateFlow = UsernameInputStateFlow(validUsernameInputValidator),
        passwordInputStateFlow = PasswordInputStateFlow(validPasswordInputValidator),
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
        onTryAgain = {},
    )
}

@Composable
@Preview
private fun LoginViewPreviewProgress() {
    LoginView(
        viewStateFlow = MutableStateFlow(LoginViewModel.State.Progress),
        usernameInputStateFlow = UsernameInputStateFlow(validUsernameInputValidator),
        passwordInputStateFlow = PasswordInputStateFlow(validPasswordInputValidator),
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
        onTryAgain = {},
    )
}

@Composable
@Preview
private fun LoginViewPreviewSuccess() {
    LoginView(
        viewStateFlow = MutableStateFlow(LoginViewModel.State.Success),
        usernameInputStateFlow = UsernameInputStateFlow(validUsernameInputValidator),
        passwordInputStateFlow = PasswordInputStateFlow(validPasswordInputValidator),
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
        onTryAgain = {},
    )
}

@Composable
@Preview
private fun LoginViewPreviewError() {
    LoginView(
        viewStateFlow = MutableStateFlow(LoginViewModel.State.Error),
        usernameInputStateFlow = UsernameInputStateFlow(validUsernameInputValidator),
        passwordInputStateFlow = PasswordInputStateFlow(validPasswordInputValidator),
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
        onTryAgain = {},
    )
}