package amaterek.movie.app.navigation

import amaterek.movie.base.navigation.Destination
import amaterek.movie.base.navigation.Direction
import androidx.compose.runtime.Immutable

@Immutable
object SplashDestination : Destination(baseRoute = "splash") {

    val movieListDirection = Direction(
        destination = MovieListDestination,
        popupTo = this,
        inclusive = true,
    )
}