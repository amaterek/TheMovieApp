package amaterek.movie.domain.model

data class MovieList(
    val items: List<Movie>,
    val loadedPages: Int,
    val totalPages: Int,
)
