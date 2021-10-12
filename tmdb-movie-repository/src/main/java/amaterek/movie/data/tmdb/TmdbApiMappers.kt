package amaterek.movie.data.tmdb

import amaterek.movie.domain.model.*
import amaterek.movie.domain.model.MovieQuery.SortBy
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

internal fun List<TmdbMovieDetailsGenre>?.toDomainGenres(): List<MovieGenre> {
    this ?: return emptyList()
    val genres = mutableSetOf<MovieGenre>()
    forEach {
        genres.addAll(it.id.toDomainGenre())
    }
    return genres.toList()
}

internal fun Int?.toDomainGenre(): List<MovieGenre> {
    return when (this) {
        28 -> listOf(MovieGenre.ACTION)
        12 -> listOf(MovieGenre.ADVENTURE)
        16 -> listOf(MovieGenre.ANIMATION)
        35 -> listOf(MovieGenre.COMEDY)
        80 -> listOf(MovieGenre.CRIME)
        99 -> listOf(MovieGenre.DOCUMENTARY)
        18 -> listOf(MovieGenre.DRAMA)
        10751 -> listOf(MovieGenre.FAMILY)
        14 -> listOf(MovieGenre.FANTASY)
        36 -> listOf(MovieGenre.HISTORY)
        27 -> listOf(MovieGenre.HORROR)
        10402 -> listOf(MovieGenre.MUSIC)
        9648 -> listOf(MovieGenre.MYSTERY)
        10749 -> listOf(MovieGenre.ROMANCE)
        878 -> listOf(MovieGenre.SCIENCE_FICTION)
        10770 -> listOf(MovieGenre.TV_MOVIE)
        53 -> listOf(MovieGenre.THRILLER)
        10752 -> listOf(MovieGenre.WAR)
        37 -> listOf(MovieGenre.WESTERN)
        10759 -> listOf(MovieGenre.ACTION, MovieGenre.ADVENTURE)
        10762 -> listOf(MovieGenre.KIDS)
        else -> emptyList()
    }
}

internal fun List<Int>?.getDomainGenres(): List<MovieGenre> {
    val result = mutableSetOf<MovieGenre>()
    this?.forEach {
        result.addAll(it.toDomainGenre())
    }
    return result.toList()
}

internal fun MovieCategory.toTmdb(): String = when (this) {
    MovieCategory.ALL -> throw NotImplementedError()
    MovieCategory.NOW_PLAYING -> "now_playing"
    MovieCategory.POPULAR -> "popular"
    MovieCategory.TOP_RATED -> "top_rated"
    MovieCategory.UPCOMING -> "upcoming"
    else -> throw IllegalArgumentException()
}

internal fun SortBy.toTmdb(): String =
    when (this) {
        SortBy.POPULARITY_DESCENDING -> "popularity.desc"
        SortBy.POPULARITY_ASCENDING -> "popularity.asc"
        SortBy.RELEASE_DATE_DESCENDING -> "release_date.desc"
        SortBy.RELEASE_DATE_ASCENDING -> "release_date.asc"
        SortBy.REVENUE_DESCENDING -> "revenue.desc"
        SortBy.REVENUE_ASCENDING -> "revenue.asc"
        SortBy.PRIMARY_RELEASE_DATE_DESCENDING -> "primary_release_date.desc"
        SortBy.PRIMARY_RELEASE_DATE_ASCENDING -> "primary_release_date.asc"
        SortBy.ORIGINAL_TITLE_DESCENDING -> "original_title.desc"
        SortBy.ORIGINAL_TITLE_ASCENDING -> "original_title.asc"
        SortBy.VOTE_AVERAGE_DESCENDING -> "vote_average.desc"
        SortBy.VOTE_AVERAGE_ASCENDING -> "vote_average.asc"
        SortBy.VOTE_COUNT_DESCENDING -> "vote_count.desc"
        SortBy.VOTE_COUNT_ASCENDING -> "vote_count.asc"
    }

internal fun Set<MovieGenre>.toTmdb(): String {
    return map { it.toTmdb() }.joinToString(",")
}

internal fun MovieGenre.toTmdb(): Int {
    return when (this) {
        MovieGenre.ACTION -> 28
        MovieGenre.ADVENTURE -> 12
        MovieGenre.ANIMATION -> 16
        MovieGenre.COMEDY -> 35
        MovieGenre.CRIME -> 80
        MovieGenre.DOCUMENTARY -> 99
        MovieGenre.DRAMA -> 18
        MovieGenre.FAMILY -> 10751
        MovieGenre.FANTASY -> 14
        MovieGenre.HISTORY -> 36
        MovieGenre.HORROR -> 27
        MovieGenre.MUSIC -> 10402
        MovieGenre.MYSTERY -> 9648
        MovieGenre.ROMANCE -> 10749
        MovieGenre.SCIENCE_FICTION -> 878
        MovieGenre.TV_MOVIE -> 10770
        MovieGenre.THRILLER -> 53
        MovieGenre.WAR -> 10752
        MovieGenre.WESTERN -> 37
        MovieGenre.KIDS -> 10762
    }
}

internal fun Locale.toTmdb(): String {
    return toString().replace('_', '-')
}

// TODO Move parsing date to Moshi adapter
@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

internal fun String?.toDomain(): Date? {
    this ?: return null
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}