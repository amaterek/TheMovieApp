package amaterek.movie.app.ui.login

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.log.Log
import amaterek.base.logTag
import amaterek.movie.app.ui.login.LoginViewModel.SideEffect.UserLoggedIn
import amaterek.movie.app.ui.login.input.PasswordInputStateFlow
import amaterek.movie.app.ui.login.input.UsernameInputStateFlow
import amaterek.movie.base.input.ValidatableTextInputState
import amaterek.movie.domain.common.LoginResult
import amaterek.movie.domain.usecase.LoginUserUseCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUserUseCase: LoginUserUseCase,
    val usernameInputStateFlow: UsernameInputStateFlow,
    val passwordInputStateFlow: PasswordInputStateFlow,
) : BaseViewModel() {

    sealed interface SideEffect {
        object UserLoggedIn : SideEffect
    }

    sealed interface State {
        object Form : State
        object Progress : State
        object Success : State
        object Error : State
    }

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _state = MutableStateFlow<State>(State.Form)
    val state = _state.asStateFlow()

    init {
        loadUsernameInputState()?.let { usernameInputStateFlow.validateAndSetValue(it) }
        usernameInputStateFlow
            .onEach(::saveUsernameInputState)
            .launchIn(viewModelScope)
    }

    fun setUsername(username: String) {
        Log.v(logTag(), "setUsername(username = $username)")
        usernameInputStateFlow.validateAndSetValue(username)
    }

    fun setPassword(password: String) {
        Log.v(logTag(), "setPassword(username = $password)") // FIXME Security warning
        passwordInputStateFlow.validateAndSetValue(password)
    }

    fun submit() {
        val username = usernameInputStateFlow.value.value
        val password = passwordInputStateFlow.value.value
        val isUsernameValid = usernameInputStateFlow.validateAndSetValue(username)
        val isPasswordValid = passwordInputStateFlow.validateAndSetValue(password)
        if (isUsernameValid && isPasswordValid) {
            onLoginStarted()
            passwordInputStateFlow.reset()
            viewModelScope.launch {
                when (loginUserUseCase(username = username, password = password)) {
                    is LoginResult.Success -> onLoginSuccess()
                    is LoginResult.Failure -> onLoginFailure()
                }
            }
        }
    }

    fun tryAgain() {
        _state.value = State.Form
    }

    private fun onLoginStarted() {
        _state.value = State.Progress
    }

    private suspend fun onLoginSuccess() {
        _state.value = State.Success
        delay(1000)
        _sideEffect.emit(UserLoggedIn)
    }

    private fun onLoginFailure() {
        _state.value = State.Error
    }

    private fun saveUsernameInputState(inputState: ValidatableTextInputState) {
        if (inputState.value != usernameInputStateFlow.value.value) {
            savedStateHandle[KEY_LOGIN_USERNAME] = inputState.value
        }
    }

    private fun loadUsernameInputState(): String? =
        savedStateHandle.get<String>(KEY_LOGIN_USERNAME)

    companion object {
        private const val KEY_LOGIN_USERNAME = "KEY_LOGIN_USERNAME"
    }
}