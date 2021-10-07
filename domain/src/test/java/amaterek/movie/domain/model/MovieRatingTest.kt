package amaterek.movie.domain.model

import amaterek.base.test.CoroutineTest
import org.junit.Test
import kotlin.test.assertFailsWith

class MovieRatingTest : CoroutineTest() {

    @Test
    fun `WHEN constructed with argument out of range THEN throws an exception`() {
        assertFailsWith<IllegalArgumentException> {
            MovieRating(-1)
        }
        assertFailsWith<IllegalArgumentException> {
            MovieRating(101)
        }
    }

    @Test
    fun `WHEN constructed with argument in range THEN just runs`() {
        for (argument in 0..100) {
            MovieRating(argument)
        }
    }
}