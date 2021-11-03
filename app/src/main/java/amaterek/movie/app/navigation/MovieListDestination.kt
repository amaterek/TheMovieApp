package amaterek.movie.app.navigation

import amaterek.movie.base.navigation.Destination
import amaterek.movie.base.navigation.Direction
import amaterek.movie.domain.model.Movie
import androidx.compose.runtime.Immutable

@Immutable
object MovieListDestination : Destination(baseRoute = "movie/list") {

    fun movieDetailsDirection(movie: Movie) = Direction(
        destination = MovieDetailsDestination,
        argument = movie.id.toString(),
    )
}