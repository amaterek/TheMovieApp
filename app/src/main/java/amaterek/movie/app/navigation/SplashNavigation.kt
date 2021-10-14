package amaterek.movie.app.navigation

import amaterek.movie.base.navigation.ScreenNavigation
import androidx.navigation.NavHostController

object SplashNavigation : ScreenNavigation(route = "splash") {

    fun navigateToMovieListScreen(navController: NavHostController) = with(navController) {
        navigate(MovieListNavigation.route) {
            popUpTo(SplashNavigation.route) { inclusive = true }
        }
    }
}