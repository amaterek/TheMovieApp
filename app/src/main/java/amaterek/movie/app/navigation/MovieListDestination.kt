package amaterek.movie.app.navigation

import amaterek.movie.app.ui.common.model.UiMovie
import amaterek.movie.base.navigation.Destination
import amaterek.movie.base.navigation.Direction
import androidx.compose.runtime.Immutable

@Immutable
object MovieListDestination : Destination(baseRoute = "movie/list") {

    fun movieDetailsDirection(movie: UiMovie) = Direction(
        destination = MovieDetailsDestination,
        argument = movie.id.toString(),
    )
}