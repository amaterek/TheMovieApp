package amaterek.movie.app.ui.login.input

import amaterek.movie.base.input.ValidatableTextInputStateFlow
import javax.inject.Inject

internal class PasswordInputStateFlow @Inject constructor(
    inputValidator: PasswordInputValidator,
) : ValidatableTextInputStateFlow(inputValidator)