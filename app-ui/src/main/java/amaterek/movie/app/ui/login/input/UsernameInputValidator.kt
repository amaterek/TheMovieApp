package amaterek.movie.app.ui.login.input

import amaterek.movie.base.input.TextInputValidator
import javax.inject.Inject

internal open class UsernameInputValidator @Inject constructor() : TextInputValidator {

    override fun validate(oldValue: String, newValue: String): TextInputValidator.Result {
        val userName = newValue.trim()
        return when {
            userName.isEmpty() ->
                TextInputValidator.Result.Error(
                    value = userName,
                    message = "Username can not be empty"
                )
            userName.length <= 3 ->
                TextInputValidator.Result.Error(
                    value = userName,
                    message = "Username does not match requirements"
                )
            else -> TextInputValidator.Result.Valid(userName)
        }
    }
}