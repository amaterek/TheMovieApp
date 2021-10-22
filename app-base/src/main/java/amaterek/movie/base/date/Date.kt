package amaterek.movie.base.date

import java.util.*

fun dateOf(
    year: Int,
    month: Int = 1,
    day: Int = 1,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0
): Date {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = year
    calendar[Calendar.MONTH] = month - 1
    calendar[Calendar.DAY_OF_MONTH] = day
    calendar[Calendar.MINUTE] = hour
    calendar[Calendar.MINUTE] = minute
    calendar[Calendar.SECOND] = second
    return calendar.time
}