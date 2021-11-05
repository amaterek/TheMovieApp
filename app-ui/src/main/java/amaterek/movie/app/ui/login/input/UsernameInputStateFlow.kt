package amaterek.movie.app.ui.login.input

import amaterek.movie.base.input.ValidatableTextInputStateFlow
import javax.inject.Inject

internal class UsernameInputStateFlow @Inject constructor(
    inputValidator: UsernameInputValidator,
) : ValidatableTextInputStateFlow(inputValidator)