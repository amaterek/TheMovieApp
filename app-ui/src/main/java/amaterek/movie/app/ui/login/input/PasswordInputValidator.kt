package amaterek.movie.app.ui.login.input

import amaterek.movie.base.input.TextInputValidator
import javax.inject.Inject

internal open class PasswordInputValidator @Inject constructor() : TextInputValidator {

    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result {
        return when {
            newValue.isEmpty() ->
                TextInputValidator.Result.Error(
                    value = newValue,
                    message = "Password can not be empty"
                )
            else ->
                TextInputValidator.Result.Valid(newValue)
        }
    }
}