package amaterek.movie.data.tmdb.mapper

import amaterek.movie.domain.model.MovieQuery.SortBy.*
import org.junit.Test
import kotlin.test.assertEquals

class TmdbSortByMappersTest {

    private val sortByMap = mapOf(
        POPULARITY_DESCENDING to "popularity.desc",
        POPULARITY_ASCENDING to "popularity.asc",
        RELEASE_DATE_DESCENDING to "release_date.desc",
        RELEASE_DATE_ASCENDING to "release_date.asc",
        REVENUE_DESCENDING to "revenue.desc",
        REVENUE_ASCENDING to "revenue.asc",
        PRIMARY_RELEASE_DATE_DESCENDING to "primary_release_date.desc",
        PRIMARY_RELEASE_DATE_ASCENDING to "primary_release_date.asc",
        ORIGINAL_TITLE_DESCENDING to "original_title.desc",
        ORIGINAL_TITLE_ASCENDING to "original_title.asc",
        VOTE_AVERAGE_DESCENDING to "vote_average.desc",
        VOTE_AVERAGE_ASCENDING to "vote_average.asc",
        VOTE_COUNT_DESCENDING to "vote_count.desc",
        VOTE_COUNT_ASCENDING to "vote_count.asc",
    )

    @Test
    fun `maps MovieCategory to TMDB category`() {
        @Suppress("UNCHECKED_CAST")
        values().forEach { sortBy ->
            assertEquals(sortByMap[sortBy], sortBy.toTmdb())
        }
    }
}