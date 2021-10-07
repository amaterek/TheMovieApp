package amaterek.movie.domain.repository

import amaterek.movie.domain.common.QueryResult
import amaterek.movie.domain.model.MovieQuery
import amaterek.movie.domain.model.*
import kotlinx.coroutines.flow.StateFlow

interface MovieRepository {

    val favoriteMoviesIds: StateFlow<Set<Long>>

    /**
     * Fetches movie list's page.
     *
     * @param query query parameters
     * @param page page to request
     * @return the movie list or error
     */
    suspend fun getMovieListPage(
        query: MovieQuery,
        page: Int
    ): QueryResult<MovieList>

    /**
     * Fetches movie's details by id and updates movieListState
     *
     * @param movieId the movie id
     * @return the movie with details or error
     */
    suspend fun getMovieDetails(movieId: Long): QueryResult<MovieDetails>

    /**
     * Sets the movie favorite or not and updates movieListState
     *
     * @param movieId the movie id
     */
    suspend fun setFavorite(movieId: Long, favorite: Boolean)
}
