package amaterek.movie.data.tmdb.mapper

import amaterek.movie.domain.model.MovieCategory
import amaterek.movie.domain.model.MovieCategory.*
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TmdbCategoryMappersTest {

    private val categoryMap = mapOf(
        ALL to NotImplementedError::class,
        NOW_PLAYING to "now_playing",
        POPULAR to "popular",
        TOP_RATED to "top_rated",
        UPCOMING to "upcoming",
        FAVORITE to NotImplementedError::class,
    )

    @Test
    fun `maps MovieCategory to TMDB category`() {
        @Suppress("UNCHECKED_CAST")
        MovieCategory.values().forEach { movieCategory ->
            when (val expected = categoryMap[movieCategory]) {
                is String -> assertEquals(expected, movieCategory.toTmdb())
                is KClass<*> -> assertFailsWith(exceptionClass = expected as KClass<out Throwable>) { movieCategory.toTmdb() }
            }
        }
    }
}