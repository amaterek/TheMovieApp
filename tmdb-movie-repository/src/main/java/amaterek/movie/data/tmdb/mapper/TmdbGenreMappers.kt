package amaterek.movie.data.tmdb.mapper

import amaterek.base.log.Log
import amaterek.movie.data.tmdb.TmdbMovieDetailsGenre
import amaterek.movie.domain.model.MovieGenre
import amaterek.movie.domain.model.MovieGenre.*

internal fun Set<MovieGenre>.toTmdb(): String {
    return map { it.toTmdb() }.joinToString(",")
}

internal fun Int?.toDomainGenre(): List<MovieGenre> {
    return when (this) {
        12 -> listOf(ADVENTURE)
        14 -> listOf(FANTASY)
        16 -> listOf(ANIMATION)
        18 -> listOf(DRAMA)
        27 -> listOf(HORROR)
        28 -> listOf(ACTION)
        35 -> listOf(COMEDY)
        36 -> listOf(HISTORY)
        37 -> listOf(WESTERN)
        53 -> listOf(THRILLER)
        80 -> listOf(CRIME)
        99 -> listOf(DOCUMENTARY)
        878 -> listOf(SCIENCE_FICTION)
        9648 -> listOf(MYSTERY)
        10402 -> listOf(MUSIC)
        10749 -> listOf(ROMANCE)
        10751 -> listOf(FAMILY)
        10752 -> listOf(WAR)
        10759 -> listOf(ACTION, ADVENTURE)
        10762 -> listOf(KIDS)
        10763 -> listOf(NEWS)
        10764 -> listOf(REALITY)
        10765 -> listOf(SCIENCE_FICTION, FANTASY)
        10766 -> listOf(SOAP)
        10767 -> listOf(TALK)
        10768 -> listOf(WAR, POLITICS)
        10770 -> listOf(TV_MOVIE)
        else -> {
            Log.w("TmdbApiGenreMappers","Can not map TMDB genre id($this) to MovieGenre")
            emptyList()
        }
    }
}

internal fun MovieGenre.toTmdb(): Int {
    return when (this) {
        ACTION -> 28
        ADVENTURE -> 12
        ANIMATION -> 16
        COMEDY -> 35
        CRIME -> 80
        DOCUMENTARY -> 99
        DRAMA -> 18
        FAMILY -> 10751
        FANTASY -> 14
        HISTORY -> 36
        HORROR -> 27
        KIDS -> 10762
        MUSIC -> 10402
        MYSTERY -> 9648
        NEWS -> 10763
        REALITY -> 10764
        ROMANCE -> 10749
        SCIENCE_FICTION -> 878
        SOAP -> 10766
        TALK -> 10767
        THRILLER -> 53
        TV_MOVIE -> 10770
        WAR -> 10752
        WESTERN -> 37
        else -> throw IllegalArgumentException("Can not map MovieGenre to TMDB: genre=$this")
    }
}

internal fun List<Int>?.getDomainGenres(): List<MovieGenre> {
    val result = mutableSetOf<MovieGenre>()
    this?.forEach { result.addAll(it.toDomainGenre()) }
    return result.toList()
}

internal fun List<TmdbMovieDetailsGenre>?.toDomainGenres(): List<MovieGenre> {
    this ?: return emptyList()
    val genres = mutableSetOf<MovieGenre>()
    forEach { genres.addAll(it.id.toDomainGenre()) }
    return genres.toList()
}