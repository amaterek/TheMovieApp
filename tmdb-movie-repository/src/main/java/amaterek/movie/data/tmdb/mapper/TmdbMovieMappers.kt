package amaterek.movie.data.tmdb.mapper

import amaterek.base.log.Log
import amaterek.movie.data.tmdb.TmdbMovie
import amaterek.movie.data.tmdb.TmdbMovieDetails
import amaterek.movie.data.tmdb.TmdbMovieListPage
import amaterek.movie.domain.model.Movie
import amaterek.movie.domain.model.MovieDetails
import amaterek.movie.domain.model.MovieList
import amaterek.movie.domain.model.MovieRating
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

private const val VOTE_AVERAGE_MAX = 10f

internal fun TmdbMovieListPage.toDomain(
    basePosterImagePath: String,
    baseBackdropImagePath: String,
    favoriteMoviesIds: Set<Long>,
): MovieList =
    MovieList(
        items = results.map {
            it.toDomain(
                basePosterImagePath,
                baseBackdropImagePath,
                favoriteMoviesIds.contains(it.id)
            )
        },
        loadedPages = page,
        totalPages = total_pages
    )

internal fun TmdbMovie.toDomain(
    basePosterImagePath: String,
    baseBackdropImagePath: String,
    isFavorite: Boolean
): Movie =
    Movie(
        id = id,
        title = title,
        rating = vote_average?.let { MovieRating((it * MovieRating.MAX / VOTE_AVERAGE_MAX).toInt()) }
            ?: MovieRating(0),
        genres = genre_ids.getDomainGenres(),
        releaseDate = release_date.toDomain(),
        posterUrl = poster_path?.let { basePosterImagePath + it }.orEmpty(),
        backdropUrl = backdrop_path?.let { baseBackdropImagePath + it }.orEmpty(),
        isFavorite = isFavorite,
    )

internal fun TmdbMovieDetails.toDomain(
    basePosterImagePath: String,
    baseBackdropImagePath: String,
    isFavorite: Boolean
): MovieDetails =
    MovieDetails(
        movie = Movie(
            id = id,
            title = title,
            rating = vote_average?.let { MovieRating((it * MovieRating.MAX / VOTE_AVERAGE_MAX).toInt()) }
                ?: MovieRating(0),
            genres = genres.toDomainGenres(),
            releaseDate = release_date.toDomain(),
            posterUrl = poster_path?.let { basePosterImagePath + it }.orEmpty(),
            backdropUrl = backdrop_path?.let { baseBackdropImagePath + it }.orEmpty(),
            isFavorite = isFavorite,
        ),
        description = overview ?: "",
        budget = budget ?: -1L
    )

internal fun Locale.toTmdb(): String {
    return toString().replace('_', '-')
}

@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

private fun String?.toDomain(): Date? {
    this ?: return null
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        Log.w("TmdbMovieMappers", "Can not parse data: $this")
        null
    }
}