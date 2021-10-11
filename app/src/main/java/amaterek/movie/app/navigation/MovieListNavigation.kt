package amaterek.movie.app.navigation

import amaterek.movie.base.navigation.ScreenNavigation
import androidx.navigation.NavHostController

object MovieListNavigation : ScreenNavigation(route = "movie/list") {

    fun navigateToMovieListScreen(navController: NavHostController, movieId: Long) =
        with(navController) {
            navigate("${MovieDetailsNavigation.route}/$movieId")
        }
}