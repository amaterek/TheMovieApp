package amaterek.movie.app.navigation

import amaterek.movie.base.navigation.Destination
import amaterek.movie.base.navigation.getArgumentFor
import androidx.compose.runtime.Immutable
import androidx.navigation.NavBackStackEntry

@Immutable
object MovieDetailsDestination :
    Destination(baseRoute = "movie/details", argumentName = "movieId") {

    fun getMovieId(navBackStackEntry: NavBackStackEntry): Long? =
        navBackStackEntry.getArgumentFor(this)?.toLong()
}