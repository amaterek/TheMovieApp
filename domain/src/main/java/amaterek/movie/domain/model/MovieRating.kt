package amaterek.movie.domain.model

data class MovieRating(val value: Int) {
    init {
        require(value in MIN..MAX)
    }

    companion object {
        const val MIN = 0
        const val MAX = 100
    }
}