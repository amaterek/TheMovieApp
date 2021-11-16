package amaterek.movie.base.moviesloader

import amaterek.movie.domain.model.Movie

data class MoviesLoaderState(
    val movies: List<Movie>,
    val loadedPages: Int,
    val totalPages: Int
) {
    companion object {
        val Empty = MoviesLoaderState(
            movies = emptyList(),
            loadedPages = 0,
            totalPages = -1,
        )
    }
}
