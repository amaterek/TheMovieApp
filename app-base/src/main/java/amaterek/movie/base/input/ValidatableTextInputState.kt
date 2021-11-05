package amaterek.movie.base.input

data class ValidatableTextInputState(
    val value: String = "",
    val error: String? = null,
)