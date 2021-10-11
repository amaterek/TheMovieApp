package amaterek.movie.app.navigation

import amaterek.movie.app.ui.moviedetails.MovieDetailsScreen
import amaterek.movie.app.ui.movielist.MovieListScreen
import amaterek.movie.app.ui.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private object Arguments {
    const val movieId = "movieId"
}

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
                onMovieClick = {
                    MovieListNavigation.navigateToMovieListScreen(
                        navController,
                        it.id
                    )
                }
            )
        }
        composable(
            route = "${MovieDetailsNavigation.route}/{${Arguments.movieId}}",
            arguments =  listOf(navArgument(Arguments.movieId) { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong(Arguments.movieId)
            MovieDetailsScreen(movieId!!) // TODO handle error if null
        }
    }
}