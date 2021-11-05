package amaterek.movie.app.ui.login.view

import amaterek.base.log.Log
import amaterek.movie.app.ui.login.input.PasswordInputStateFlow
import amaterek.movie.app.ui.login.input.UsernameInputStateFlow
import amaterek.movie.theme.view.AppPasswordInputField
import amaterek.movie.theme.view.AppTextButton
import amaterek.movie.theme.view.AppTextInputData
import amaterek.movie.theme.view.AppTextInputField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.map

private val usernameInputDataPrototype = AppTextInputData(
    label = "Username",
    hint = "email@example.com",
)

private val passwordInputDataPrototype = AppTextInputData(
    label = "Password",
    hint = "your secret password",
)

@Composable
internal fun LoginFormView(
    usernameInputStateFlow: UsernameInputStateFlow,
    passwordInputStateFlow: PasswordInputStateFlow,
    onUsernameValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Log.v("ComposeRender", "LoginFormScreen")

    val usernameInputState = usernameInputStateFlow.map {
        usernameInputDataPrototype.copy(
            value = it.value,
            error = it.error,
        )
    }.collectAsState(usernameInputDataPrototype)

    val passwordInputState = passwordInputStateFlow.map {
        passwordInputDataPrototype.copy(
            value = it.value,
            error = it.error,
        )
    }.collectAsState(passwordInputDataPrototype)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "Login") },
        )
        LoginUsernameInputField(
            inputState = usernameInputState,
            onUsernameValueChanged = onUsernameValueChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
        )
        LoginPasswordInputField(
            inputState = passwordInputState,
            onPasswordValueChanged = onPasswordValueChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = { onSubmit() }
            )
        )
        AppTextButton(
            text = "Login",
            onClick = onSubmit,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun LoginUsernameInputField(
    inputState: State<AppTextInputData>,
    onUsernameValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
) {
    Log.v("ComposeRender", "LoginUsernameInputField")

    AppTextInputField(
        inputState = inputState,
        onValueChanged = onUsernameValueChanged,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun LoginPasswordInputField(
    inputState: State<AppTextInputData>,
    onPasswordValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    Log.v("ComposeRender", "LoginPasswordInputField")

    AppPasswordInputField(
        inputState = inputState,
        onValueChanged = onPasswordValueChanged,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
@Preview
private fun ValidLoginFormPreview() {
    LoginFormView(
        usernameInputStateFlow = UsernameInputStateFlow(validUsernameInputValidator).apply {
            validateAndSetValue("")
        },
        passwordInputStateFlow = PasswordInputStateFlow(validPasswordInputValidator).apply {
            validateAndSetValue("")
        },
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
    )
}

@Composable
@Preview
private fun InvalidLoginFormPreview() {
    LoginFormView(
        usernameInputStateFlow = UsernameInputStateFlow(invalidUsernameInputValidator).apply {
            validateAndSetValue("")
        },
        passwordInputStateFlow = PasswordInputStateFlow(invalidPasswordInputValidator).apply {
            validateAndSetValue("")
        },
        onUsernameValueChanged = {},
        onPasswordValueChanged = {},
        onSubmit = {},
    )
}