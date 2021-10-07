package amaterek.movie.domain.model

data class MovieDetails(
    val movie: Movie,
    val description: String,
    val budget: Long, // [dollars] It would be worth to introduce dedicated class
)