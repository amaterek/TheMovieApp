package amaterek.movie.base.input

interface TextInputValidator {

    sealed interface Result {
        data class Valid(val value: String) : Result
        data class Error(val value: String, val message: String?) : Result
    }

    fun validate(oldValue: String, newValue: String): Result
}