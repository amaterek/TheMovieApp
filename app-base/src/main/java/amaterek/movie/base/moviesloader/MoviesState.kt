package amaterek.movie.base.moviesloader

import amaterek.movie.domain.model.Movie

data class MoviesState(
    val movies: List<Movie>,
    val hasMore: Boolean,
)
