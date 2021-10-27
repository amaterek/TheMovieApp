package amaterek.movie.base.date

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class DateTest {

    @Test
    fun `dateOf returns correct date`() {
        val year = 2022
        val month = 1
        val day = 2
        val hour = 3
        val minute = 4
        val second = 5

        assertEquals(
            @Suppress("DEPRECATION") Date(
                year - 1900,
                month - 1,
                day,
                hour,
                minute,
                second
            ),
            dateOf(year, month, day, hour, minute, second)
        )
    }
}