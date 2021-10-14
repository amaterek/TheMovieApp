package amaterek.movie.app.navigation

import amaterek.movie.app.ui.movielist.MovieListScreen
import amaterek.movie.app.ui.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashNavigation.route
    ) {
        composable(route = SplashNavigation.route) {
            SplashScreen(
                onSplashFinished = { SplashNavigation.navigateToMovieListScreen(navController) },
            )
        }
        composable(route = MovieListNavigation.route) {
            MovieListScreen(
                onMovieClick = { }
            )
        }
    }
}