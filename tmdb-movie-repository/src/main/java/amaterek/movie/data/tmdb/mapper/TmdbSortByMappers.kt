package amaterek.movie.data.tmdb.mapper

import amaterek.movie.domain.model.MovieQuery.SortBy
import amaterek.movie.domain.model.MovieQuery.SortBy.*

internal fun SortBy.toTmdb(): String =
    when (this) {
        POPULARITY_DESCENDING -> "popularity.desc"
        POPULARITY_ASCENDING -> "popularity.asc"
        RELEASE_DATE_DESCENDING -> "release_date.desc"
        RELEASE_DATE_ASCENDING -> "release_date.asc"
        REVENUE_DESCENDING -> "revenue.desc"
        REVENUE_ASCENDING -> "revenue.asc"
        PRIMARY_RELEASE_DATE_DESCENDING -> "primary_release_date.desc"
        PRIMARY_RELEASE_DATE_ASCENDING -> "primary_release_date.asc"
        ORIGINAL_TITLE_DESCENDING -> "original_title.desc"
        ORIGINAL_TITLE_ASCENDING -> "original_title.asc"
        VOTE_AVERAGE_DESCENDING -> "vote_average.desc"
        VOTE_AVERAGE_ASCENDING -> "vote_average.asc"
        VOTE_COUNT_DESCENDING -> "vote_count.desc"
        VOTE_COUNT_ASCENDING -> "vote_count.asc"
    }
