package amaterek.movie.base.input

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class ValidatableTextInputStateFlow(
    private val validator: TextInputValidator,
    private val stateFlow: MutableStateFlow<ValidatableTextInputState> = MutableStateFlow(ValidatableTextInputState()),
) : StateFlow<ValidatableTextInputState> by stateFlow {

    fun validateAndSetValue(value: String) =
        validateInput(validator, value)

    private fun validateInput(
        inputValidator: TextInputValidator,
        value: String,
    ): Boolean {
        val inputState = when (val validationResult =
            inputValidator.validate(stateFlow.value.value, value)) {
            is TextInputValidator.Result.Valid -> ValidatableTextInputState(
                value = validationResult.value,
                error = null
            )
            is TextInputValidator.Result.Error -> ValidatableTextInputState(
                value = validationResult.value,
                error = validationResult.message
            )
        }
        stateFlow.value = inputState
        return inputState.error == null
    }

    fun reset() {
        stateFlow.value = ValidatableTextInputState()
    }
}