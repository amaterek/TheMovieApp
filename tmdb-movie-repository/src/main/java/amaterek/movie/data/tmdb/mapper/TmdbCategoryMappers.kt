package amaterek.movie.data.tmdb.mapper

import amaterek.movie.domain.model.*

internal fun MovieCategory.toTmdb(): String = when (this) {
    MovieCategory.ALL -> throw NotImplementedError()
    MovieCategory.NOW_PLAYING -> "now_playing"
    MovieCategory.POPULAR -> "popular"
    MovieCategory.TOP_RATED -> "top_rated"
    MovieCategory.UPCOMING -> "upcoming"
    MovieCategory.FAVORITE -> throw NotImplementedError()
}