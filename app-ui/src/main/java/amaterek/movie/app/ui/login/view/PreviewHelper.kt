package amaterek.movie.app.ui.login.view

import amaterek.movie.app.ui.login.input.PasswordInputValidator
import amaterek.movie.app.ui.login.input.UsernameInputValidator
import amaterek.movie.base.input.TextInputValidator

internal val validUsernameInputValidator = object : UsernameInputValidator() {
    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result =
        TextInputValidator.Result.Valid("My name")
}

internal val invalidUsernameInputValidator = object : UsernameInputValidator() {
    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result =
        TextInputValidator.Result.Error("My name", "Username error")
}

internal val validPasswordInputValidator = object : PasswordInputValidator() {
    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result =
        TextInputValidator.Result.Valid("xyz")
}

internal val invalidPasswordInputValidator = object : PasswordInputValidator() {
    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result =
        TextInputValidator.Result.Error("xyz", "Password error")
}