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
    calendar[Calendar.HOUR_OF_DAY] = hour
    calendar[Calendar.MINUTE] = minute
    calendar[Calendar.SECOND] = second
    calendar[Calendar.MILLISECOND] = 0
    return calendar.time
}